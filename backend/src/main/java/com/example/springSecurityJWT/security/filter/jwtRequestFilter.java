package com.example.springSecurityJWT.security.filter;

import com.example.springSecurityJWT.configuration.jwtUtils;
import com.example.springSecurityJWT.domain.member;
import com.example.springSecurityJWT.dto.userCustomDetails;
import com.example.springSecurityJWT.repository.memberRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
*   Authorization filter!
*   
* */


@Slf4j
@Component
public class jwtRequestFilter extends BasicAuthenticationFilter {

    private final jwtUtils jwtUtils;
    private final memberRepository memberRepository;

    public jwtRequestFilter(AuthenticationManager authenticationManager, memberRepository memberRepository, jwtUtils jwtUtils) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        /**
         * http header 에서 AUTHORIZATION 정보 가지고 오기
         * => token 유무 확인해서 로그인 체크
         * */
        logger.info("jwtRequestFilter 작동!");

        String authorizationHeader = request.getHeader("Authorization");
        logger.info("JwtHeader : " + authorizationHeader);

        // AuthorizationHeader 에 정보가 없을 때
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer")){
            logger.error("authorizationHeader is invalid!");
            filterChain.doFilter(request, response);
            return;
        }

        Claims jwtClaims = jwtUtils.tokenInfo(authorizationHeader);

        if (jwtClaims != null) {
            String username = (String) jwtClaims.get("username");
            logger.info("jwtClaims username : " + username);
            member member = memberRepository.findByUsername(username);

            userCustomDetails userCustomDetails = new userCustomDetails(member);
            // JWT authorization 정상일 경우 authentication 객체 생성!
            Authentication authentication = new UsernamePasswordAuthenticationToken
                    (userCustomDetails, null, userCustomDetails.getAuthorities());

            // security session 에 authentication 저장!
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
