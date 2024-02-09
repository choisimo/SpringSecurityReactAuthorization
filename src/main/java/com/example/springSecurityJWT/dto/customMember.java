package com.example.springSecurityJWT.dto;

import com.example.springSecurityJWT.domain.auth;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.springSecurityJWT.domain.member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public class customMember implements UserDetails {

    private member member;

    public customMember(member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<auth> authList = member.getAuthList();
        Collection<SimpleGrantedAuthority> roles =
                authList.stream().
                        map(auth -> new SimpleGrantedAuthority(auth.getAuth())).toList();
        return roles;
    }

    @Override
    public String getPassword() {
        return member.getMemberPw();
    }

    @Override
    public String getUsername() {
        return member.getMemberName();
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
