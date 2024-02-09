package com.example.springSecurityJWT.repository;

import com.example.springSecurityJWT.domain.auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface authRepository extends JpaRepository<auth, Long> {
}
