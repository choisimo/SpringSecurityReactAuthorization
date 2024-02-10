package com.example.springSecurityJWT.dto;

import com.example.springSecurityJWT.domain.Role;
import com.example.springSecurityJWT.domain.member;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class registerRequest {

    private Long id;
    private String password;
    private String username;
    private String regdate;
    private boolean enabled;
    private Role role;


    @Builder
    public registerRequest(String username, String password, String regdate, boolean enabled, Role role) {
        this.password = password;
        this.username = username;
        this.regdate = regdate;
        this.enabled = enabled;
        this.role = role;
    }

    public member toEntity() {
        return member.builder()
                .username(username)
                .password(password)
                .regdate(LocalDateTime.now().toString())
                .enabled(true)
                .build();
    }
}
