package com.example.springSecurityJWT.configuration;

import com.example.springSecurityJWT.repository.memberRepository;
import com.example.springSecurityJWT.security.filter.jwtAuthenticationFilter;
import com.example.springSecurityJWT.security.filter.jwtRequestFilter;
import com.example.springSecurityJWT.service.userCustomDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;

@Slf4j
@Configuration
@EnableWebSecurity
public class securityConfig {

    @Autowired
    private userCustomDetailService userCustomDetailService;
    private AuthenticationManager authenticationManager;
    private memberRepository memberRepository;
    @Autowired
    private jwtUtils jwtUtils;
    @Autowired
    private CorsConfigurationSource corsConfigurationSource;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        log.info("SecurityFilterChain");

        // disable CSRF attack protection
        http.csrf(AbstractHttpConfigurer::disable);

        // HTTP basic authentication disable
        http.httpBasic(AbstractHttpConfigurer::disable);

        // set Cors
        http.cors(cors -> cors.configurationSource(corsConfigurationSource));

        // form login disable
        http.formLogin(AbstractHttpConfigurer::disable);

        // disable session authentication policy
        http.sessionManagement(management ->
                management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // to use custom Authentication, set null
        http.userDetailsService(userCustomDetailService);

        // jwtAuthenticationFilter 객체 생성 시 authenticationManager 생성자에서 생성될 수 있도록!!
        http.addFilterAt(new jwtAuthenticationFilter(authenticationManager, jwtUtils, objectMapper())
                        , UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new jwtRequestFilter(authenticationManager,memberRepository, jwtUtils)
                        , UsernamePasswordAuthenticationFilter.class);

        // set authorization
        http.authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
        );

        http.formLogin(formLogin -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/"));
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

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}
