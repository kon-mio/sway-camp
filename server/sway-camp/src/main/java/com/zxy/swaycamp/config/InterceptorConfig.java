package com.zxy.swaycamp.config;


import com.zxy.swaycamp.common.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 *
 * @author XinYuan Zhao
 * @since 2023/1/25
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * Token拦截配置
     *
     * @param registry 请求
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor())
                // 拦截所有请求，通过判断token是否合法来决定是否需要登录
                .addPathPatterns("/**")
                //开放接口
                .excludePathPatterns("/api/test/**");

    }

    /**
     * 注册拦截器
     */
    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }
}