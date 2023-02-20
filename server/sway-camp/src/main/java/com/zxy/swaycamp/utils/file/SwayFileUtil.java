package com.zxy.swaycamp.utils.file;

import com.zxy.swaycamp.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * 文件工具类
 * @author XinYuan Zhao
 * @since 2023/2/20
 */
public class SwayFileUtil {
    private static final Logger logger = LoggerFactory.getLogger(SwayFileUtil.class);

    /**
     * 密码提花
     */
    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

    /**
     * 获取图片类型
     * @param file 图片文件
     * @return type/null
     */
    public static String getType(MultipartFile file){
        //文件类型判断
        try{
            return FileTypeUtil.getFileType(file.getInputStream());
        }catch (Exception e){
            logger.error("获取文件类型错误: {}", e.getMessage());
            throw new ServiceException();
        }
    }

    /**
     * MultipartFile 转 File
     * @param file MultipartFile
     * @return File
     */
    public static File multipartFileToFile(MultipartFile file){
        File toFile = null;
        try{
            if(file.getSize() > 0){
                InputStream ins = file.getInputStream();
                toFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
                OutputStream os = Files.newOutputStream(toFile.toPath());
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                ins.close();
            }
            return toFile;
        } catch (Exception e){
            logger.error("文件类型转换错误: {}", e.getMessage());
            throw new ServiceException();
        }
    }

    /**
     * 获取文件Md5
     * @param multipartFile 文件
     * @return md5
     */
    public static String compMd5(MultipartFile multipartFile) {
        try {
            InputStream stream = multipartFile.getInputStream();
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] buf = new byte[8192];
            int len;
            while ((len = stream.read(buf)) > 0) {
                digest.update(buf, 0, len);
            }
            StringBuilder md5 = new StringBuilder(digest.digest().length * 2);
            for (byte b : digest.digest()) {
                md5.append(hexCode[(b >> 4) & 0xF]);
                md5.append(hexCode[(b & 0xF)]);
            }
            stream.close();
            return md5.toString();
        } catch (Exception e){
            logger.error("获取文件md5错误: {}", e.getMessage());
            throw new ServiceException();
        }
    }

    public static String toHexString(byte[] data) {
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }
}
