package com.zxy.swaycamp.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.zxy.swaycamp.common.enums.CodeMsg;
import com.zxy.swaycamp.common.exception.ServiceException;
import com.zxy.swaycamp.domain.dto.anime.AnimeDTO;
import com.zxy.swaycamp.domain.entity.*;
import com.zxy.swaycamp.domain.vo.FileVO;
import com.zxy.swaycamp.domain.vo.anime.AnimeVO;
import com.zxy.swaycamp.mapper.AnimeLabelMapper;
import com.zxy.swaycamp.mapper.AnimeLabelMergeMapper;
import com.zxy.swaycamp.mapper.AnimeMapper;
import com.zxy.swaycamp.mapper.AnimeRecommendMapper;
import com.zxy.swaycamp.service.AnimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.swaycamp.service.OssService;
import com.zxy.swaycamp.utils.SwayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
            throw new ServiceException();
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
              //new LambdaUpdateChainWrapper<>(animeRecommendMapper)
              //        .eq(AnimeRecommend::getId, recommendId)
              //        .set(AnimeRecommend::getDeleted, true)
              //        .update();
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("上传动漫信息报错：{}", e.getMessage());
            throw new ServiceException();
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
     * 判断是否含有动漫
     * @return pure -> true
     */
    private Boolean hasAnime(Integer animeId){
        Anime anime = lambdaQuery().eq(Anime::getId, animeId).eq(Anime::getIsDeleted, false).one();
        return anime == null ? false : true;
    }

}
