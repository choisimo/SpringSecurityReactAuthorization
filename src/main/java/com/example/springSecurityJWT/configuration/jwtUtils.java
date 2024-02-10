package com.example.springSecurityJWT.configuration;

import com.example.springSecurityJWT.domain.member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class jwtUtils {

    private final Key key;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public jwtUtils(@Value("${jwt.secret-key}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createToken(String username, Collection< ? extends GrantedAuthority> role){
        Claims claims = Jwts.claims();
        claims.put("username", username);
        claims.put("role", role);

        long TOKEN_VALIDATION = 86400000L;

        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDATION))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .compact();
    }

    // Parse and return JWT claims
    public Claims tokenInfo(String header){
        logger.info(header);
        if (header.startsWith("Bearer ")){
            String jwt = header.split(" ")[1];
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } else {
            return null;
        }
    }
}
