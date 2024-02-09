package com.example.springSecurityJWT.service;

import com.example.springSecurityJWT.domain.member;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface memberService {

    public int insert(member member) throws Exception;

    public member select(long id);

    public void login(member member, HttpServletRequest request) throws Exception;

    public int update(member member) throws Exception;

    public int delete(String id) throws Exception;

    public member login(String memberName) throws Exception;

}
