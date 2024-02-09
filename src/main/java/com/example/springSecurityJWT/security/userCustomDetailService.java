package com.example.springSecurityJWT.security;

import com.example.springSecurityJWT.domain.member;
import com.example.springSecurityJWT.dto.customMember;
import com.example.springSecurityJWT.service.memberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class userCustomDetailService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private memberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("login loadUserByUsername" + username);

        member member = null;
        try {
            member = memberService.login(username);
        } catch (Exception e) {
            logger.info("일치 하는 사용자 없음!!");
            throw new RuntimeException(e);
        }

        customMember customMember = new customMember(member);

        logger.info("customMember : " + customMember.toString());

        return customMember;
    }
}
