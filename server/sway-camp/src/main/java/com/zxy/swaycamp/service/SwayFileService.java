package com.zxy.swaycamp.service;

import com.zxy.swaycamp.domain.entity.SwayFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxy.swaycamp.domain.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * <p>
 * 文件信息表 服务类
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-20
 */
public interface SwayFileService extends IService<SwayFile> {

    ///**
    // * 上传文件
    // * @param file 文件
    // * @param userId 业务ID
    // * @return 文件ID
    // */
    //FileVO uploadImage(MultipartFile file, Integer userId);
    //
    ///**
    // * 写入文件
    // * @param file 文件
    // * @return Boolean
    // */
    //Boolean saveFile(MultipartFile file, File saveFile);

}
