package com.example.springSecurityJWT.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
public class member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "member_password", length = 100)
    private String memberPw;
    @Column(name = "member_name", length = 15)
    private String memberName;
    private String regdate;
    private boolean enabled;

    @Builder
    public member(Long id, String memberPw, String memberName, String regdate, boolean enabled) {
        this.id = id;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.regdate = regdate;
        this.enabled = enabled;
    }

    public member() {}
}
