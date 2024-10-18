package com.nrapendra.helpers;


import com.nrapendra.account.config.AppConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        AppConfig.class
})
public class TestConfig {
}
