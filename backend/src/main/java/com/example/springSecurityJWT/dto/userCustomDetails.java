package com.example.springSecurityJWT.dto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.springSecurityJWT.domain.member;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;


@Slf4j
public class userCustomDetails implements UserDetails {

    private member member;

    public userCustomDetails(member member) {
        log.info("userCustomDetails 실행!");
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(new SimpleGrantedAuthority(member.getRole().name())).toList();
    }

    @Override
    public String getPassword() {
        if (member.getPassword() != null){
            log.info("userDetails password is exist");
        }
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        log.info("userDetails username : " + member.getUsername());
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
