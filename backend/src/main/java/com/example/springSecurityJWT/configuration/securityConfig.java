package com.example.springSecurityJWT.configuration;

import com.example.springSecurityJWT.repository.memberRepository;
import com.example.springSecurityJWT.security.filter.jwtAuthenticationFilter;
import com.example.springSecurityJWT.security.filter.jwtRequestFilter;
import com.example.springSecurityJWT.service.userCustomDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Slf4j
@Configuration
@EnableWebSecurity
public class securityConfig {

    @Autowired
    private userCustomDetailService userCustomDetailService;
    private AuthenticationManager authenticationManager;
    private memberRepository memberRepository;
    private CorsConfiguration corsConfiguration;
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        log.info("SecurityFilterChain");

        // disable CSRF attack protection
        http.csrf(AbstractHttpConfigurer::disable);

        // HTTP basic authentication disable
        http.httpBasic(AbstractHttpConfigurer::disable);

        // form login disable
        http.formLogin(AbstractHttpConfigurer::disable);

        // disable session authentication policy
        http.sessionManagement(management ->
                management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // to use custom Authentication, set null
        http.userDetailsService(userCustomDetailService);

        // jwtAuthenticationFilter 객체 생성 시 authenticationManager 생성자에서 생성될 수 있도록!!
        http.addFilterAt(new jwtAuthenticationFilter(authenticationManager)
                        , UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new jwtRequestFilter(authenticationManager,memberRepository)
                        , UsernamePasswordAuthenticationFilter.class);

        // set authorization
        http.authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().permitAll()
        );

        return http.build();
    }

    // BcryptPasswordEncoder
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
        throws Exception {
        log.info("AuthenticationManager Bean");
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        return authenticationManager;
    }
}
