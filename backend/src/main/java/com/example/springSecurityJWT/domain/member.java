package com.example.springSecurityJWT.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Entity
@Data
public class member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String password;
    @Column(length = 15)
    private String username;
    private String regdate;
    private boolean enabled;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;


    @Builder
    public member(Long id, String password, String username, String regdate, boolean enabled, Role role) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.regdate = regdate;
        this.enabled = enabled;
        this.role = role;
    }

    public member() {}
}
