package com.han.train.member.config;

import com.han.train.common.interceptor.LogInterceptor;
import com.han.train.common.interceptor.MemberInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringInterceptorConfig implements WebMvcConfigurer {

    @Resource
    MemberInterceptor memberInterceptor;

    @Resource
    LogInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 路径不要包含context-path，并且拦截器是手动开启的，那个模块需要就开启
        registry.addInterceptor(logInterceptor);

        registry.addInterceptor(memberInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/hello",
                        "/member/send-code",
                        "/member/login"
                );

    }
}
