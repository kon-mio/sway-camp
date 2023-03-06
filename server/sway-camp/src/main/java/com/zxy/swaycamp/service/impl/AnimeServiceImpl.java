package com.zxy.swaycamp.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.model.PutObjectRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.zxy.swaycamp.common.constant.FileConstant;
import com.zxy.swaycamp.common.constant.HttpStatus;
import com.zxy.swaycamp.common.enums.CodeMsg;
import com.zxy.swaycamp.common.exception.ServiceException;
import com.zxy.swaycamp.domain.dto.anime.AnimeDTO;
import com.zxy.swaycamp.domain.entity.*;
import com.zxy.swaycamp.domain.vo.FileVO;
import com.zxy.swaycamp.domain.vo.anime.AnimeVO;
import com.zxy.swaycamp.domain.vo.anime.ImageVO;
import com.zxy.swaycamp.mapper.*;
import com.zxy.swaycamp.service.AnimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.swaycamp.service.OssService;
import com.zxy.swaycamp.utils.SwayUtil;
import com.zxy.swaycamp.utils.file.SwayFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 动漫信息表 服务实现类
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-03-06
 */
@Service
public class AnimeServiceImpl extends ServiceImpl<AnimeMapper, Anime> implements AnimeService {

    private static final Logger logger = LoggerFactory.getLogger(AnimeServiceImpl.class);

    @Resource
    private OssService ossService;
    @Resource
    private AnimeLabelMapper animeLabelMapper;
    @Resource
    private AnimeLabelMergeMapper animeLabelMergeMapper;
    @Resource
    private AnimeRecommendMapper animeRecommendMapper;

    @Resource
    private AnimeImageMapper animeImageMapper;


    /**
     * 获取动漫信息
     * @param animeId 动漫ID
     * @return 动漫信息
     */
    @Override
    public AnimeVO getAnime(Integer animeId){
        Anime anime = lambdaQuery().eq(Anime::getId, animeId).one();
        if(anime == null){
            throw new ServiceException("动漫不存在");
        }
        AnimeVO animeVO = new AnimeVO();
        BeanUtils.copyProperties(anime, animeVO);

        List<AnimeLabelMerge> animeLabelMerges = new LambdaQueryChainWrapper<>(animeLabelMergeMapper)
                .eq(AnimeLabelMerge::getAnimeId, anime.getId())
                .list();
        animeVO.setLabels(animeLabelMerges.stream().map(item -> item.getLabelName()).collect(Collectors.toList()));
        List<AnimeImage> animeImages = new LambdaQueryChainWrapper<>(animeImageMapper)
                .eq(AnimeImage::getAnimeId, anime.getId())
                .eq(AnimeImage::getIsDeleted, false)
                .list();
        animeVO.setImages(animeImages.stream().map(item -> {
            ImageVO imageVO = new ImageVO();
            BeanUtils.copyProperties(item, imageVO);
            return imageVO;
        }).collect(Collectors.toList()));
        return animeVO;
    }

    /**
     * 新增动漫
     * 动漫信息和标签出错会统一回滚
     *
     * @param animeDTO 动漫信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAnime(AnimeDTO animeDTO){
        List<String> region = new ArrayList<>();
        region.add("日本");
        region.add("大陆");
        if(!region.contains(animeDTO.getRegion())){
            throw new ServiceException("地区错误");
        }
        Integer userId = SwayUtil.getCurrentUserId();
        FileVO fileVo = ossService.uploadImage(animeDTO.getCover(), userId, "image/anime/cover/");
        if(fileVo == null){
            throw new ServiceException(CodeMsg.PARAMETER_ERROR);
        }
        try{
            Anime anime = new Anime();
            BeanUtils.copyProperties(animeDTO, anime);
            anime.setCover(fileVo.getUrl());
            anime.setCreateTime(LocalDateTime.now());
            anime.setUpdateTime(LocalDateTime.now());
            anime.setIsDeleted(false);
            save(anime);
            Integer animeId = anime.getId();
            animeDTO.getLabels().forEach(item ->{
                AnimeLabel animeLabel = new LambdaQueryChainWrapper<>(animeLabelMapper)
                        .eq(AnimeLabel::getLabelName, item)
                        .one();
                if(animeLabel == null){
                    throw new ServiceException("动漫标签不存在");
                }
            });
            animeDTO.getLabels().forEach(item ->{
                AnimeLabelMerge animeLabelMerge = new AnimeLabelMerge();
                animeLabelMerge.setAnimeId(animeId);
                animeLabelMerge.setLabelName(item);
                animeLabelMerge.setCreateTime(LocalDateTime.now());
                animeLabelMerge.setUpdateTime(LocalDateTime.now());
                animeLabelMerge.setIsDeleted(false);
                animeLabelMergeMapper.insert(animeLabelMerge);
            });
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("上传动漫信息报错：{}", e.getMessage());
            throw new ServiceException("上传失败");
        }
    }

    /**
     * 新增推荐动漫
     * @param animeId 动漫ID
     * @param labelName 推荐标签
     */
    @Override
    public void saveRecommendAnime(Integer animeId, String labelName){
        try{

            // TODO 未判断动漫是否包含该标签
            if(!hasAnime(animeId)){
                throw new ServiceException("动漫不存在");
            }
            AnimeLabel animeLabel = new LambdaQueryChainWrapper<>(animeLabelMapper)
                    .eq(AnimeLabel::getLabelName, labelName)
                    .one();
            if(animeLabel == null){
                throw new ServiceException("动漫标签不存在");
            }

            // TODO 若deleted字段设置默认值并且字段使用 @TableLogic 字段， 则mybatis-plus会自动添加条件deleted = false
            AnimeRecommend animeRecommend = new LambdaQueryChainWrapper<>(animeRecommendMapper)
                    .eq(AnimeRecommend::getAnimeId, animeId)
                    .one();

            if(animeRecommend != null){
                if(!animeRecommend.getDeleted()){
                    throw new ServiceException("重复推荐");
                }
                // 标签相同且已删除
                if(animeRecommend.getLabelName().equals(labelName)){
                    new LambdaUpdateChainWrapper<>(animeRecommendMapper)
                            .eq(AnimeRecommend::getId, animeRecommend.getId())
                            .set(AnimeRecommend::getDeleted, false)
                            .update();
                }else{
                    new LambdaUpdateChainWrapper<>(animeRecommendMapper)
                            .eq(AnimeRecommend::getId, animeRecommend.getId())
                            .set(AnimeRecommend::getLabelName, labelName)
                            .set(AnimeRecommend::getDeleted, false)
                            .update();
                }
            }
            if(animeRecommend == null){
                animeRecommend = new AnimeRecommend();
                animeRecommend.setAnimeId(animeId);
                animeRecommend.setLabelName(labelName);
                animeRecommend.setCreateTime(LocalDateTime.now());
                animeRecommend.setUpdateTime(LocalDateTime.now());
                animeRecommend.setDeleted(false);
                animeRecommendMapper.insert(animeRecommend);
            }
        }catch (Exception e){
            logger.error("上传推荐动漫信息报错：{}", e.getMessage());
            throw new ServiceException();
        }
    }

    /**
     * 取消推荐动漫
     * @param recommendId 推荐ID
     */
    @Override
    public void removeRecommendAnime(Integer recommendId){
        AnimeRecommend animeRecommend = new LambdaQueryChainWrapper<>(animeRecommendMapper)
                .eq(AnimeRecommend::getId, recommendId)
                .eq(AnimeRecommend::getDeleted, false)
                .one();

        logger.info(String.valueOf(animeRecommend));
        if(animeRecommend == null){
            throw new ServiceException();
        }
        try{
              new LambdaUpdateChainWrapper<>(animeRecommendMapper)
                      .eq(AnimeRecommend::getId, recommendId)
                      .set(AnimeRecommend::getDeleted, true)
                      .update();
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("上传动漫信息报错：{}", e.getMessage());
            throw new ServiceException("上传失败");
        }
    }


    /**
     * 查询推荐动漫列表
     * @param label 推荐标签
     * @return 查询推荐动漫列表
     */
    @Override
    public List<AnimeVO> listRecommendAnime(String label){
        List<AnimeRecommend> animeRecommends = label == null ?
                new LambdaQueryChainWrapper<>(animeRecommendMapper)
                .eq(AnimeRecommend::getDeleted, false)
                .list() :
                new LambdaQueryChainWrapper<>(animeRecommendMapper)
                .eq(AnimeRecommend::getLabelName, label)
                .eq(AnimeRecommend::getDeleted, false)
                .list();
        List<AnimeVO> animeVOS = animeRecommends.stream().filter(item -> hasAnime(item.getAnimeId())).map(item -> getAnime(item.getAnimeId())).collect(Collectors.toList());
        return animeVOS;
    }

    /**
     * 上传动漫图片
     * @param files 图片文件
     */
    @Override
    public void uploadAnimeImage(MultipartFile[] files, Integer animeId){
        if(!hasAnime(animeId)){
            throw new ServiceException("动漫不存在");
        }
        Integer userId = SwayUtil.getCurrentUserId();
        try{
            Arrays.stream(files).forEach(item->{
                FileVO fileVO =  ossService.uploadImage(item, userId, "image/anime/picture");
                AnimeImage animeImage = new AnimeImage();
                animeImage.setAnimeId(animeId);
                animeImage.setUrl(fileVO.getUrl());
                animeImage.setCreateTime(LocalDateTime.now());
                animeImage.setUpdateTime(LocalDateTime.now());
                animeImage.setIsDeleted(false);
                //获取宽高
                try {
                    InputStream ins = item.getInputStream();
                    BufferedImage bufferedImage = ImageIO.read(ins);
                    animeImage.setWidth(String.valueOf(bufferedImage.getWidth()));
                    animeImage.setHeight(String.valueOf(bufferedImage.getHeight()));
                    ins.close();
                } catch (IOException e) {
                    logger.error("获取图片信息失败：{}", e.getMessage());
                    throw new ServiceException("上传失败");
                }
                animeImageMapper.insert(animeImage);
            });
        }catch (Exception e){
            logger.error("上传动漫图片错误：{}", e.getMessage());
            throw new ServiceException("上传失败");
        }

    }


    /**
     * 判断是否含有动漫
     * @return pure -> true
     */
    private Boolean hasAnime(Integer animeId){
        Anime anime = lambdaQuery().eq(Anime::getId, animeId).eq(Anime::getIsDeleted, false).one();
        return anime == null ? false : true;
    }


    /**
     * 校验图片是否合规
     */
    private void validImage(MultipartFile[] files){
        String[] imageTypes = {"jpg", "png", "webp", "jpeg"};
        if(files.length == 0){
            throw new ServiceException(CodeMsg.PARAMETER_ERROR);
        }
        Arrays.stream(files).forEach(item->{
            //文件类型
            String fileType = SwayFileUtil.getType(item);
            if(!StringUtils.hasText(fileType)){
                throw new ServiceException(CodeMsg.PARAMETER_ERROR);
            }
            // 判断是否是图片
            if(!Arrays.asList(imageTypes).contains(fileType)){
                throw new ServiceException(HttpStatus.BAD_REQUEST, "图片类型错误");
            }
            //文件原始昵称
            String originalFilename = item.getOriginalFilename();
            //后缀名
            String extName = FileUtil.extName(originalFilename);
            //文件大小
            long size = item.getSize();
            if(size > FileConstant.FILE_SIZE_MAX_KB){
                throw new ServiceException(HttpStatus.BAD_REQUEST, "文件大小不符合规范");
            }
        });
    }
}
