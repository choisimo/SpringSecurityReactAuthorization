package com.example.springSecurityJWT.configuration;

import com.example.springSecurityJWT.repository.memberRepository;
import com.example.springSecurityJWT.security.filter.jwtAuthenticationFilter;
import com.example.springSecurityJWT.security.filter.jwtRequestFilter;
import com.example.springSecurityJWT.service.userCustomDetailService;
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
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class securityConfig {

    @Autowired
    private userCustomDetailService userCustomDetailService;
    private AuthenticationManager authenticationManager;
    private memberRepository memberRepository;
    private CorsConfiguration corsConfiguration;
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
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

        http.addFilter(new jwtAuthenticationFilter(authenticationManager))
                .addFilter(new jwtRequestFilter(authenticationManager,memberRepository));

        // set authorization
        http.authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
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
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        return authenticationManager;
    }
}
