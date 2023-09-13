package com.gmail.robertzylan.atiperaTask.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiConfiguration implements WebMvcConfigurer {

    @Bean
    public WrongApiCallInterceptor undesiredHeaderInterceptor() {
        return new WrongApiCallInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(undesiredHeaderInterceptor());
    }
}