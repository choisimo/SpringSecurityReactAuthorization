package com.example.springSecurityJWT.controller;

import com.example.springSecurityJWT.configuration.jwtUtils;
import com.example.springSecurityJWT.dto.AuthenticationRequest;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class loginController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final jwtUtils jwtUtils;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();

        logger.info("request username : " + username);
        logger.info("request password : " + password);

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");

        // create Token
        String jwt = jwtUtils.createToken(username, roles);

        logger.info("create Token : " + jwt);

        return new ResponseEntity<String>(jwt, HttpStatus.OK);
    }

    @GetMapping("/token/info")
    public ResponseEntity<?> tokenInfo(@RequestHeader(name = "Authorization") String header) {
        Claims parsedToken = jwtUtils.tokenInfo(header);

        if (parsedToken != null) {
            String username = parsedToken.get("username", String.class);
            List authorites = parsedToken.get("auth", List.class);

            logger.info("username : " + username);
            logger.info("authorites" + authorites);

            return new ResponseEntity<String>(parsedToken.toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("invalid or missing token", HttpStatus.UNAUTHORIZED);
        }
    }
}
