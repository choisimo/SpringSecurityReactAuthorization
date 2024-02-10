package com.example.springSecurityJWT.service;

import com.example.springSecurityJWT.domain.member;
import com.example.springSecurityJWT.dto.AuthenticationRequest;
import com.example.springSecurityJWT.repository.memberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class memberService {

    private PasswordEncoder passwordEncoder;
    private memberRepository memberRepository;

    private AuthenticationManager authenticationManager;

    public int insert(member member) throws Exception {
        String memberPw = member.getUsername();

        member.setPassword(passwordEncoder.encode(memberPw));

        memberRepository.save(member);

        return 1;
    }

    public void login(AuthenticationRequest request) throws Exception {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // create token
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken
                        (request.getUsername(), request.getPassword());

        // set request info to the token
        token.setDetails(new WebAuthenticationDetails(request));

        // login request via token
        Authentication authentication = authenticationManager.authenticate(token);

        log.info("check authentication" + authentication.isAuthenticated());

        member authmember = (member) authentication.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
