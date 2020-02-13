package com.ww.gmall.config;

import com.ww.gmall.interceptors.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.List;

@Configuration
/**
 * springboot 2中废除了WebMvcConfigurerAdapter,而使用WebMvcConfigurer接口，其余不变，去除super调用即可
 */
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    AuthInterceptor authInterceptor;

    /**
     * 拦截器不拦截所有的静态资源
     */
    private static List<String> EXCLUDE_PATH= Arrays.asList("/js/**","/css/**","/img/**","/error","/json/**","/index/**");

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns(EXCLUDE_PATH);
    }

}
