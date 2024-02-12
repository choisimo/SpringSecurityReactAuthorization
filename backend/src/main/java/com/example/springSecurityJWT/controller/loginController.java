package com.example.springSecurityJWT.controller;

import com.example.springSecurityJWT.configuration.jwtUtils;
import com.example.springSecurityJWT.domain.Role;
import com.example.springSecurityJWT.domain.member;
import com.example.springSecurityJWT.dto.AuthenticationRequest;
import com.example.springSecurityJWT.dto.registerRequest;
import com.example.springSecurityJWT.service.memberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class loginController {

    private final jwtUtils jwtUtils;
    private final memberService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody registerRequest request) throws Exception{
        log.info("register controller start!");

        registerRequest regRequest = registerRequest.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .role(Role.USER)
                .build();

        log.info(String.valueOf(regRequest));

        member member = regRequest.toEntity();

        int result = memberService.insert(member);

        log.info("register insert result : " + result);

        if (result == 1){
            log.info("registration success!");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.error("registration failed!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public void login(@RequestBody AuthenticationRequest request){
        log.info("login controller");
    }

    @PostMapping("/home")
    @ResponseBody
    public String home(){
        log.info("postMapping home executed");
        return "springSecurity";
    }
}
