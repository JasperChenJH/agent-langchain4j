package com.cqupt.java.ai.langchain4j.config;

import com.cqupt.java.ai.langchain4j.tokenInterceptor.TokenInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * @description: 拦截器配置类
 * @author: jie
 * @time: 2023/11/7 16:07
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final Logger log = LoggerFactory.getLogger(WebConfig.class);
    @Autowired
    // 注入拦截器
    TokenInterceptor tokenInterceptor;
    @Override
    // 拦截器注册
    public void addInterceptors(InterceptorRegistry registry) {

        // 拦截所有请求,注册拦截器,放行/login
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
//                        "/xiaozhi/chat",
                        "/login",
                        "/doc.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**"
                );
    }
}
