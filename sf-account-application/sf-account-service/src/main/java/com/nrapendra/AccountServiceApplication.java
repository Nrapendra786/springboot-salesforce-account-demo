package com.nrapendra;

import com.nrapendra.account.config.SalesforceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableConfigurationProperties(SalesforceConfig.class)
@EnableTransactionManagement
@Slf4j
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
		log.info("ACCOUNT APPLICATION STARTED");
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		return new LocalContainerEntityManagerFactoryBean();
	}
}
