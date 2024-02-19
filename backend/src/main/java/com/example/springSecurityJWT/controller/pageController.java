package com.example.springSecurityJWT.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class pageController {

    @GetMapping("/admin/1")
    public String adminPage() {
        log.info("admin page controller");
        return "admin page 1";
    }

    @PostMapping("/user/1")
    public String userPage(){
        log.info("user page controller");
        return "user page 1";
    }

    
}
