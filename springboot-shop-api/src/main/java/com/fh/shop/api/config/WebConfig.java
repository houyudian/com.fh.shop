package com.fh.shop.api.config;

import com.fh.shop.api.interceptor.IdempotentInterceptor;
import com.fh.shop.api.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 给springboot注册一个loginInterceptor拦截器，并且拦截/**，即拦截所有资源
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/api/**");
        registry.addInterceptor(idempotentInterceptor()).addPathPatterns("/api/**");
    }

    /**
     * 等同于 <bean id="loginInterceptor" class="com.fh.shop.api.interceptor.LoginInterceptor"></bean>
     *
     * @return
     */
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }
    @Bean
    public IdempotentInterceptor idempotentInterceptor() {
        return new IdempotentInterceptor();
    }
}
