package com.example.springSecurityJWT.security.filter;

import com.example.springSecurityJWT.configuration.jwtUtils;
import com.example.springSecurityJWT.dto.AuthenticationRequest;
import com.example.springSecurityJWT.dto.userCustomDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;


@Slf4j
public class jwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 인증 시도 메소드 -> "/login" request filter
     *  /login 호출시 UsernamePasswordAuthenticationFilter 낚아챔!!
     *  -> username, password 받아주기!!
     */

    //사용자 인증을 위한 authenticationManager!
    private final AuthenticationManager authenticationManager;

    private final jwtUtils jwtUtils;

    private final ObjectMapper objectMapper;
    // constructor
    public jwtAuthenticationFilter(AuthenticationManager authenticationManager, jwtUtils jwtUtils, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.objectMapper = objectMapper;
    }

    /**
    * authenticationManager 가 로그인 시도를 하면
    * -> userCustomDetailsService 호출!
    */

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.info("jwtAuthenticationFilter 작동!");

        AuthenticationRequest authenticationRequest;
        try {
            authenticationRequest = objectMapper.readValue(request.getInputStream(), AuthenticationRequest.class);
        } catch (IOException e) {
            log.error("JSON inputStream 실패!");
            throw new RuntimeException(e);
        }

        log.info("username : " + authenticationRequest.getUsername());

        // 사용자 인증정보 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(), authenticationRequest.getPassword());

        log.info("사용자 인증 정보 토큰 생성 완료!");

        // 사용자 인증처리
        // authentication 함수 호출 시
        // userCustomDetailsService loadUserByUsername 실행
        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        // 인증 유무 확인
        if (!authentication.isAuthenticated()) {
            log.error("authentication failed! 401 UNAUTHORIZED!");
            response.setStatus(401);
        }

        log.info("check authentication : " + authentication.isAuthenticated());

        userCustomDetails userCustomDetails =
                (userCustomDetails) authentication.getPrincipal();

        log.info("userCustomDetails username : " + userCustomDetails.getUsername());

        return authentication;
    }

    /*
    * attemptAuthentication successful
    * create JWT and return JWT with response
    * */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("successfulAuthentication executed");

        userCustomDetails userCustomDetails = (userCustomDetails) authResult.getPrincipal();

        String jwtToken = jwtUtils.createToken
                (userCustomDetails.getUsername(), userCustomDetails.getAuthorities());

        log.info("login generated Token : " + jwtToken);

        response.addHeader("Authorization", "Bearer " + jwtToken);
        response.setStatus(200);
    }
}
