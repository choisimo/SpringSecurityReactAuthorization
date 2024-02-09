package com.example.springSecurityJWT.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "member_name", length = 15)
    private String memberName;
    @Column(length = 10)
    private String auth;


    @Builder
    public auth(long id, String memberName, String auth) {
        this.id = id;
        this.memberName = memberName;
        this.auth = auth;
    }

    public auth() {

    }
}
