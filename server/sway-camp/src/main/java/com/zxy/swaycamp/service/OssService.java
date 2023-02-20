package com.zxy.swaycamp.service;

import com.zxy.swaycamp.domain.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author XinYuan Zhao
 * @since 2023/2/20
 */
public interface OssService {
    /**
     * 上传图片
     * @param file 文件
     * @param userId 用户Id
     * @param path 图片路径 例 image/
     * @return 文件链接
     */
    FileVO uploadImage(MultipartFile file, Integer userId, String path);
}
