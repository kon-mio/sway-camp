package com.zxy.swaycamp.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxy.swaycamp.common.constant.FileConstant;
import com.zxy.swaycamp.common.constant.HttpStatus;
import com.zxy.swaycamp.common.enums.CodeMsg;
import com.zxy.swaycamp.common.exception.ServiceException;
import com.zxy.swaycamp.config.AliOssConfig;
import com.zxy.swaycamp.domain.entity.SwayFile;
import com.zxy.swaycamp.domain.vo.FileVO;
import com.zxy.swaycamp.service.OssService;
import com.zxy.swaycamp.service.SwayFileService;
import com.zxy.swaycamp.utils.file.SwayFileUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author XinYuan Zhao
 * @since 2023/2/20
 */
@Service
public class OssServiceImpl implements OssService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private AliOssConfig ossConfig;
    @Resource
    private OSS ossClient;
    @Resource
    private SwayFileService swayFileService;


    /**
     * 上传图片
     * @param file 文件
     * @param userId 用户Id
     * @param path 图片路径 例 image/
     * @return 文件链接
     */
    @Override
    public FileVO uploadImage(MultipartFile file, Integer userId, String path){

        String[] imageTypes = {"jpg", "png", "webp", "jpeg"};

        if(file == null){
            throw new ServiceException(CodeMsg.PARAMETER_ERROR);
        }

        //上传文件
        SwayFile swayFile;
        FileVO fileVo = new FileVO();

        //文件类型
        String fileType = SwayFileUtil.getType(file);
        if(!StringUtils.hasText(fileType)){
            throw new ServiceException(CodeMsg.PARAMETER_ERROR);
        }
        // 判断是否是图片
        if(!Arrays.asList(imageTypes).contains(fileType)){
            throw new ServiceException(HttpStatus.BAD_REQUEST, "图片类型错误");
        }

        //文件原始昵称
        String originalFilename = file.getOriginalFilename();
        //后缀名
        String extName = FileUtil.extName(originalFilename);

        //文件大小
        long size = file.getSize();
        if(size > FileConstant.FILE_SIZE_MAX_KB){
            throw new ServiceException(HttpStatus.BAD_REQUEST, "文件大小不符合规范");
        }
        //如果文件存在直接返回文件信息
        String md5 = SwayFileUtil.compMd5(file);
        logger.info(md5);
        swayFile = swayFileService.getOne(new LambdaQueryWrapper<SwayFile>().eq(SwayFile::getMd5, md5));
        if(swayFile != null){
            fileVo.setUrl(swayFile.getUrl());
            fileVo.setId(swayFile.getId());
            return fileVo;
        }else{
            swayFile = new SwayFile();
        }

        // 定义一个文件唯一的uuid昵称
        String fileName =  IdUtil.fastSimpleUUID() + StrUtil.DOT + fileType;

        //存储信息到数据库
        String url = "https://sway-camp.oss-cn-qingdao.aliyuncs.com/" + path + fileName;

        swayFile.setCreateTime(LocalDateTime.now());
        swayFile.setFileType("Image");
        swayFile.setUserId(userId);
        swayFile.setSize(String.valueOf(size));
        swayFile.setSubmitName(originalFilename);
        swayFile.setStatus(true);
        swayFile.setExtension(extName);
        swayFile.setMd5(md5);
        swayFile.setName(fileName);
        swayFile.setUrl(url);
        swayFile.setRelativePath(path);
        swayFile.setIsDeleted(false);

        try {
            File uploadFile = SwayFileUtil.multipartFileToFile(file);
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(ossConfig.getBucketName(),path + fileName, uploadFile);
            // 上传文件到云端
            ossClient.putObject(putObjectRequest);
            swayFileService.save(swayFile);
            fileVo.setId(swayFile.getId());
            fileVo.setUrl(url);
            uploadFile.delete();
            return fileVo;
        } catch (Exception e) {
            logger.error("上传文件错误: {}", e.getMessage());
            throw new ServiceException();
        }
    }
}
