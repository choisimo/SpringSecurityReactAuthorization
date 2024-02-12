package com.example.springSecurityJWT.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class pageController {

    @GetMapping("/admin/1")
    @ResponseBody
    public String adminPage() {
        return "admin page";
    }

    @GetMapping("/user/1")
    @ResponseBody
    public String userPage(){
        return "user page";
    }

    
}
