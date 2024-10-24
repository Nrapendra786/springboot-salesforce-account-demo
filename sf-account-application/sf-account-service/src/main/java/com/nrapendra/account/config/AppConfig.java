package com.nrapendra.account.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "salesforce")
@Data
public class AppConfig {
    private String user;
    private String password;
    private String clientId;
    private String clientSecret;
    private String tokenUrl;
    private String securityToken;

}
