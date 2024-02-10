package com.example.springSecurityJWT.security.filter;

import com.example.springSecurityJWT.configuration.jwtUtils;
import com.example.springSecurityJWT.dto.userCustomDetails;
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

    private jwtUtils jwtUtils;

    // constructor
    public jwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/login");
    }

    /**
    * authenticationManager 가 로그인 시도를 하면
    * -> userCustomDetailsService 호출!
    */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        logger.info("username" + username);
        logger.info("password" + password);

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(username, password);

        // userCustomDetailsService loadUserByUsername 실행
        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        if (!authentication.isAuthenticated()) {
            logger.error("authentication failed! 401 UNAUTHORIZED!");
            response.setStatus(401);
        }

        logger.info("check authentication : " + authentication.isAuthenticated());

        userCustomDetails userCustomDetails =
                (userCustomDetails) authentication.getPrincipal();

        logger.info("userCustomDetails username : " + userCustomDetails.getUsername());

        return authentication;
    }

    /*
    * attemptAuthentication successful
    * create JWT and return JWT with response
    * */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        logger.info("successfulAuthentication executed");

        userCustomDetails userCustomDetails = (userCustomDetails) authResult.getPrincipal();

        String jwtToken = jwtUtils.createToken
                (userCustomDetails.getUsername(), userCustomDetails.getAuthorities());

        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}
