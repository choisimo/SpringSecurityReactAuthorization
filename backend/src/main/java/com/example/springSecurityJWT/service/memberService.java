package com.example.springSecurityJWT.service;

import com.example.springSecurityJWT.domain.member;
import com.example.springSecurityJWT.repository.memberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class memberService {

    private PasswordEncoder passwordEncoder;
    private memberRepository memberRepository;

    private AuthenticationManager authenticationManager;

    public int insert(member member) throws Exception {
        String memberPw = member.getPassword();
        String username = member.getUsername();

        member checkMember = memberRepository.findByUsername(username);

        if (checkMember != null) {
            log.error("이미 존재하는 username 입니다!");
            return 0;
        }
        log.info("memberService, register's member password : " + memberPw);
        log.info("memberService, register's member username : " + username);

        member.setPassword(passwordEncoder.encode(memberPw));

        memberRepository.save(member);
        return 1;
    }
}
