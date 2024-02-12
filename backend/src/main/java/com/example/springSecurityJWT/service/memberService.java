package com.example.springSecurityJWT.service;

import com.example.springSecurityJWT.domain.member;
import com.example.springSecurityJWT.repository.memberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class memberService {

    private final PasswordEncoder passwordEncoder;
    private final memberRepository memberRepository;

    public int insert(member member) throws Exception {

        String memberPw = member.getPassword();
        String username = member.getUsername();

        member checkMember = memberRepository.findByUsername(username);

        if (checkMember != null) {
            log.error("이미 존재하는 username 입니다!");
            return 0;
        }
        log.info("memberService, register's member username : " + username);

        member.setPassword(passwordEncoder.encode(memberPw));

        try{
            memberRepository.save(member);
            log.info("memberRepository.save success!");
            return 1;
        } catch(Exception e) {
            log.error("memberRepository.save error! : " + e);
            return -1;
        }
    }
}
