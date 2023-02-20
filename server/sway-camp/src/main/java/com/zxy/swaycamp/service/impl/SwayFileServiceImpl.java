package com.zxy.swaycamp.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxy.swaycamp.common.constant.HttpStatus;
import com.zxy.swaycamp.common.enums.CodeMsg;
import com.zxy.swaycamp.common.exception.ServiceException;
import com.zxy.swaycamp.domain.entity.SwayFile;
import com.zxy.swaycamp.domain.vo.FileVO;
import com.zxy.swaycamp.mapper.SwayFileMapper;
import com.zxy.swaycamp.service.SwayFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.swaycamp.utils.file.SwayFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 文件信息表 服务实现类
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-20
 */
@Service
public class SwayFileServiceImpl extends ServiceImpl<SwayFileMapper, SwayFile> implements SwayFileService {

    //private static final Logger logger = LoggerFactory.getLogger(SwayFileServiceImpl.class);
    //
    //@Value("${file.path.image}")
    //public String imagePath;
    //
    //public final  String commonUrl = "file.konmio.com";
    //
    //
    ///**
    // * 上传图片文件
    // * @param file 文件
    // * @param userId 业务ID
    // * @return 文件ID
    // */
    //@Override
    //public FileVO uploadImage(MultipartFile file, Integer userId){
    //    if(file == null){
    //        throw new ServiceException(CodeMsg.PARAMETER_ERROR);
    //    }
    //    //文件类型
    //    String fileType = null;
    //    try {
    //        fileType = FileTypeUtil.getType(file.getInputStream());
    //    } catch (IOException e) {
    //        logger.error("获取文件类型出错：{}", e.getMessage());
    //    }
    //    if(StrUtil.isBlank(fileType)){
    //        throw new ServiceException(CodeMsg.PARAMETER_ERROR);
    //    }
    //    //文件原始昵称
    //    String originalFilename = file.getOriginalFilename();
    //    //后缀名
    //    String extName = FileUtil.extName(originalFilename);
    //    //文件大小
    //    long size = file.getSize();
    //    if((size/(1024*1024)) > 5 ){
    //        throw new ServiceException(HttpStatus.BAD_REQUEST, "文件大小不符合规范");
    //    }
    //    //上传文件
    //    SwayFile swayFile = new SwayFile();
    //    FileVO fileVo = new FileVO();
    //
    //    //如果文件存在直接返回文件信息
    //    String md5 = null;
    //    try{
    //        md5 = SwayFileUtil.calcMD5(file.getInputStream());
    //        swayFile = lambdaQuery().eq(SwayFile::getMd5, md5).one();
    //        if(swayFile != null){
    //            fileVo.setUrl(swayFile.getUrl());
    //            fileVo.setId(swayFile.getId());
    //            return fileVo;
    //        }
    //    }catch (Exception e){
    //        logger.error("获取文件md5: {}", e.getMessage());
    //
    //    }
    //
    //    // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
    //    File filePath = new File(imagePath);
    //    if (!filePath.isDirectory()) {
    //        filePath.mkdirs();
    //    }
    //
    //    // 定义一个文件唯一的uuid昵称
    //    String fileName =  IdUtil.fastSimpleUUID() + StrUtil.DOT + fileType;
    //    //构建真实的文件路径
    //    File uploadFile = new File(filePath.getAbsoluteFile() + File.separator + fileName);
    //    if(Boolean.FALSE.equals(saveFile(file, uploadFile))){
    //        throw new ServiceException(CodeMsg.FAIL);
    //    }
    //
    //    //存储信息到数据库
    //    String nginxUrl = "/" + "image" + "/" + fileName;
    //    String url = "http://" + commonUrl + nginxUrl;
    //
    //    swayFile.setCreateTime(LocalDateTime.now());
    //    swayFile.setFileType("Image");
    //    swayFile.setUserId(userId);
    //    swayFile.setSize(String.valueOf(size));
    //    swayFile.setSubmitName(originalFilename);
    //    swayFile.setStatus(true);
    //    swayFile.setExtension(extName);
    //    swayFile.setMd5(md5);
    //    swayFile.setName(fileName);
    //    swayFile.setUrl(url);
    //    swayFile.setRelativePath("/image");
    //    swayFile.setIsDeleted(false);
    //    try{
    //        save(swayFile);
    //    }catch (Exception e){
    //        throw new ServiceException(CodeMsg.FAIL);
    //    }
    //
    //    fileVo.setId(swayFile.getId());
    //    fileVo.setUrl(url);
    //    return fileVo;
    //}
    //
    //
    ///**
    // * 写入文件
    // * @param file 文件
    // * @return Boolean
    // */
    //@Override
    //public Boolean saveFile(MultipartFile file,File saveFile){
    //    try {
    //        // 上传文件到磁盘
    //        file.transferTo(saveFile);
    //        return true;
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //        logger.error(String.valueOf(e));
    //        return false;
    //    }
    //}

}
