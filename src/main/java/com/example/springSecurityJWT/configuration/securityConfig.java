package com.example.springSecurityJWT.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class securityConfig {

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // disable CSRF attack protection
        http.csrf(AbstractHttpConfigurer::disable);

        // HTTP basic authorization disable
        http.httpBasic(AbstractHttpConfigurer::disable);

        // form login disable
        http.formLogin(AbstractHttpConfigurer::disable);

        // disable session authorization policy
        http.sessionManagement(management ->
                management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
