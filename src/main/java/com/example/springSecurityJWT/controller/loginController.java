package com.example.springSecurityJWT.controller;

import com.example.springSecurityJWT.configuration.jwtUtils;
import com.example.springSecurityJWT.domain.Role;
import com.example.springSecurityJWT.domain.member;
import com.example.springSecurityJWT.dto.AuthenticationRequest;
import com.example.springSecurityJWT.dto.registerRequest;
import com.example.springSecurityJWT.service.memberService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class loginController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final jwtUtils jwtUtils;
    private memberService memberService;
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();

        logger.info("request username : " + username);
        logger.info("request password : " + password);

        return new ResponseEntity<AuthenticationRequest>(memberService.login(request), HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody registerRequest request) throws Exception{
        registerRequest regRequest = registerRequest.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .role(Role.USER)
                .build();

        member member = regRequest.toEntity();

        return new ResponseEntity<Integer>(memberService.insert(member), HttpStatus.OK);
    }
}
