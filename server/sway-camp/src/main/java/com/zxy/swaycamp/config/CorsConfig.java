package com.zxy.swaycamp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 跨域配置
 *
 * @author XinYuan Zhao
 * @since 2023/1/23
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //  设置访问源地址
        corsConfiguration.addAllowedOrigin("*");
        //  设置访问源请求头
        corsConfiguration.addAllowedHeader("*");
        //  设置访问源请求方法
        corsConfiguration.addAllowedMethod("*");

        //  是否允许请求带有验证信息
        //corsConfiguration.setAllowCredentials(true);
        //List<String> allowedOriginPatterns = new ArrayList<>();
        //allowedOriginPatterns.add("*");
        //corsConfiguration.setAllowedOriginPatterns(allowedOriginPatterns);

        //  对接口配置跨域设置
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);

    }
}
