package com.example.springSecurityJWT.service;

import com.example.springSecurityJWT.domain.auth;
import com.example.springSecurityJWT.domain.member;
import com.example.springSecurityJWT.repository.authRepository;
import com.example.springSecurityJWT.repository.memberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class memberServiceImpl implements memberService {

    private PasswordEncoder passwordEncoder;
    private final memberRepository memberRepository;
    private final authRepository authRepository;
    private AuthenticationManager authenticationManager;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public int insert(member member) throws Exception {
            String memberPw = member.getMemberPw();

            member.setMemberPw(passwordEncoder.encode(memberPw));

            auth memberAuth = auth.builder()
                    .memberName(member.getMemberName())
                    .auth("ROLE_USER")
                    .build();

            authRepository.save(memberAuth);
            memberRepository.save(member);

            return 1;
        }

    @Override
    public member select(long id) {
        return null;
    }

    @Override
    public void login(member member, HttpServletRequest request) throws Exception {
        String memberName = member.getMemberName();
        String memberPw = member.getMemberPw();

        Optional<member> optionalMember = memberRepository.findByMemberName(memberName);
        if (optionalMember.isEmpty()){
            logger.error("member from database send null");
        } else {
            boolean checkPassword = passwordEncoder.matches
                    (optionalMember.get().getMemberPw(), memberPw);
            if (checkPassword) {
                // create token
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(memberName, memberPw);

                // set request info to the token
                token.setDetails(new WebAuthenticationDetails(request));

                // login request via token
                Authentication authentication = authenticationManager.authenticate(token);

                logger.info("check authentication" + authentication.isAuthenticated());

                member authmember = (member) authentication.getPrincipal();

                logger.info("인증된 사용자 ! " + authmember.getMemberName());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
    }

    @Override
    public int update(member member) throws Exception {
        return 0;
    }

    @Override
    public int delete(String id) throws Exception {
        return 0;
    }
}
