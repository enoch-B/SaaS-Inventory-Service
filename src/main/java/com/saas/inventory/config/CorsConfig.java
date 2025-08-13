 package com.saas.inventory.config;

 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.security.config.Customizer;
 import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
 import org.springframework.security.web.SecurityFilterChain;
 import org.springframework.web.servlet.config.annotation.CorsRegistry;

 import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

 @Configuration
// @EnableWebMvc
 public class CorsConfig implements WebMvcConfigurer {


     @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");

    }
 }
