package com.example.springSecurityJWT.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class jwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 인증 시도 메소드 -> "/login" request filter
     */

    private final AuthenticationManager authenticationManager;

    // constructor
    public jwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/login");
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String memberName = request.getParameter("memberName");
        String memberPw = request.getParameter("memberPw");

        logger.info("memberName" + memberName);
        logger.info("memberPw" + memberPw);

        
        // 사용자 인증 정보 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(memberName, memberPw);
        
        // 사용자 인증 로그인
        authentication = authenticationManager.authenticate(authentication);

        logger.info("check authentication : " + authentication.isAuthenticated());

        if (!authentication.isAuthenticated()) {
            logger.error("authentication failed! 401 UNAUTHORIZED!");
            response.setStatus(401);
        }
        return authentication;
    }

    /*
    *
    * */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

    }
}
