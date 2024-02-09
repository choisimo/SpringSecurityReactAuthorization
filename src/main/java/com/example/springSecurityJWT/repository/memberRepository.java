package com.example.springSecurityJWT.repository;

import com.example.springSecurityJWT.domain.member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface memberRepository extends JpaRepository<member, Long> {
    Optional<member> findByMemberName(String memberName);
}
