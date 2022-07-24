package com.gmail.at.kotamadeo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user").setViewName("users/user");
        registry.addViewController("/admin").setViewName("admins/admin");
        registry.addViewController("/login").setViewName("login/login");
    }
}
