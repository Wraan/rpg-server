package com.rpg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    //TODO test if annotation is enough, if not try below code
//    @Bean
//    public WebSecurityConfigurerAdapter webSecurity() {
//        return new WebSecurityConfigurerAdapter() {
//
//            @Override
//            protected void configure(HttpSecurity http) throws Exception {
//                http.headers().addHeaderWriter(
//                        new StaticHeadersWriter("Access-Control-Allow-Origin", "*"));
//
//
//            }
//        };
//    }
}
