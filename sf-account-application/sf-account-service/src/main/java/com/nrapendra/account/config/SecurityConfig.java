package com.nrapendra.account.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.nrapendra.account.utils.AppUtil.ROLE;
import static com.nrapendra.account.utils.AppUtil.SWAGGER_UI;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${db-user}")
    private String dbUser;

    @Value("${db-password}")
    private String dbPassword;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/").permitAll()
                                .requestMatchers(req -> req.getRequestURI()
                                        .contains(SWAGGER_UI)).permitAll()
                                .anyRequest().authenticated())
                .httpBasic(withDefaults())
                .headers(t -> t.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername(dbUser)
                .password("{noop}" + dbPassword) // NoOpPasswordEncoder for demo purposes
                .roles(ROLE)
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}