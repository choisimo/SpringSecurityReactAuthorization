package com.example.springSecurityJWT.service;

import com.example.springSecurityJWT.domain.member;
import com.example.springSecurityJWT.dto.userCustomDetails;
import com.example.springSecurityJWT.repository.memberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
* 사용자 이름을 받아서 사용자 인증 확인 (loadUserByUsername) 후,
* 인증 확인된 사용자일 경우 -> UserDetails 를 상속 받는 useCustomDetails 를
* return
* */
@Service
@RequiredArgsConstructor
public class userCustomDetailService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final memberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("login loadUserByUsername : " + username);

        member member = null;
        try {
            member = memberRepository.findByUsername(username);
        } catch (Exception e) {
            logger.info("일치 하는 사용자 없음!!");
            throw new RuntimeException(e);
        }

        userCustomDetails userCustomDetails = new userCustomDetails(member);

        logger.info("customMember : " + userCustomDetails.toString());

        return userCustomDetails;
    }
}
