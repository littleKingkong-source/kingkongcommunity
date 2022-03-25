package com.littlekingkong.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/22 14:47*@since 1.0.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterRegistry sessionInterRegistry;

    String path [] = {"/publish/**","/profile/**"};
    String excludePath [] = {"/"};


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterRegistry).addPathPatterns(path).excludePathPatterns(excludePath);
    }
}
