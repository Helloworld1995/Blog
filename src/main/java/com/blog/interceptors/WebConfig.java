package com.blog.interceptors;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                             .addPathPatterns("/admin/**")//拦截所有访问该路径的请求
                                .excludePathPatterns("/admin").excludePathPatterns("/admin/login");//排除这些路径
    }
}
