package com.github.RehanKhan1704.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.Module;

@Configuration
public class JacksonConfig {
    @Bean
    public Module springDataPageModule() {
        return new org.springframework.data.web.config.SpringDataJacksonConfiguration.PageModule(null);
    }
}