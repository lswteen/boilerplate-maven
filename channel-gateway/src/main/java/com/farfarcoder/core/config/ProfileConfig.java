package com.farfarcoder.core.config;

import com.farfarcoder.util.ProfileUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ProfileConfig {
    @Bean
    public ProfileUtil profileUtil(Environment environment) {
        return new ProfileUtil(environment);
    }
}
