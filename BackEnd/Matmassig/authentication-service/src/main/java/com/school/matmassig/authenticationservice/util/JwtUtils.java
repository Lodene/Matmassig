package com.school.matmassig.authenticationservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.school.matmassig.authenticationservice.model.User;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils implements InitializingBean {
    @Value("${jwt.secret}")
    private String secret;

    private Key secretKey;

    @Override
    public void afterPropertiesSet() {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("roles", user.getRole())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Expire après 1 heure
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }
}