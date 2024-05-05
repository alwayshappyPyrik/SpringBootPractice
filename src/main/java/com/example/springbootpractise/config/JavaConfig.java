package com.example.springbootpractise.config;

import com.example.springbootpractise.profile.DevProfile;
import com.example.springbootpractise.profile.ProductionProfile;
import com.example.springbootpractise.profile.SystemProfile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {
    @Bean(name = "devProfile")
    @ConditionalOnProperty(prefix = "profile", name = "dev")
    public SystemProfile devProfile() {
        return new DevProfile();
    }

    @Bean(name = "prodProfile")
    @ConditionalOnProperty(prefix = "profile", name = "production")
    public SystemProfile prodProfile() {
        return new ProductionProfile();
    }
}
