package com.example.springSecurityJWT.security.filter;

import com.example.springSecurityJWT.configuration.jwtUtils;
import com.example.springSecurityJWT.dto.userCustomDetails;
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
     */

    //사용자 인증을 위한 authenticationManager!
    private final AuthenticationManager authenticationManager;

    private jwtUtils jwtUtils;

    // constructor
    public jwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        log.info("jwtAuthenticationFilter 작동!");
    }

    /**
    * authenticationManager 가 로그인 시도를 하면
    * -> userCustomDetailsService 호출!
    */

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.info("jwtAuthenticationFilter 작동!");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        log.info("username" + username);
        log.info("password" + password);

        // 사용자 인증정보 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(username, password);

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

        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}
