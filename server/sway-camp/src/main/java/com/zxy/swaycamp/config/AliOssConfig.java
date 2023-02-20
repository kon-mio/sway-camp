package com.zxy.swaycamp.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.BucketReferer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XinYuan Zhao
 * @since 2023/2/20
 */

@Configuration
@Component
public class AliOssConfig {
    private static OSS ossClient;

    private static OSSClientBuilder ossClientBuilder;

    private static String endpoint;

    private static String accessKeyId;

    private static String accessKeySecret;

    @Value("${aliyun.bucketName}")
    private  String bucketName;

    @Value("${aliyun.endpoint}")
    public void setEndpoint(String endpoint) {
        AliOssConfig.endpoint = endpoint;
    }

    @Value("${aliyun.accessKeyId}")
    public void setAccessKeyId(String accessKeyId) {
        AliOssConfig.accessKeyId = accessKeyId;
    }

    @Value("${aliyun.accessKeySecret}")
    public void setAccessKeySecret(String accessKeySecret) {
        AliOssConfig.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }


    @Bean
    @Scope("prototype")
    public static OSS initOssClient() {
        if (ossClient == null) {
            synchronized (AliOssConfig.class) {
                if (ossClient == null) {
                    ossClient = initOssClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
                    List<String> refererList = new ArrayList<String>();
                    // 添加Referer白名单。Referer参数支持通配符星号（*）和问号（?）。
                    refererList.add("*.konmio.com");
                    refererList.add("127.0.0.1");
                    // 设置存储空间Referer列表。设为true表示Referer字段允许为空，设为false表示Referer字段不允许为空。
                    BucketReferer br = new BucketReferer(true, refererList);
                    ossClient.setBucketReferer("konmio", br);
                }
            }
        }
        return ossClient;
    }

    public static OSSClientBuilder initOssClientBuilder() {
        if (ossClientBuilder == null) {
            synchronized (AliOssConfig.class) {
                if (ossClientBuilder == null) {
                    ossClientBuilder = new OSSClientBuilder();
                }
            }
        }
        return ossClientBuilder;
    }
}
