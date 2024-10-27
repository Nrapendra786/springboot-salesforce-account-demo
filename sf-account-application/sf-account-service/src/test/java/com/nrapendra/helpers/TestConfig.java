package com.nrapendra.helpers;


import com.nrapendra.account.config.SalesforceConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        SalesforceConfig.class
})
public class TestConfig {
}
