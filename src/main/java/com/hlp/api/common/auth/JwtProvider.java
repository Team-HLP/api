package com.hlp.api.common.auth;

import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hlp.api.admin.user.model.Admin;
import com.hlp.api.common.auth.exception.InvalidJwtException;
import com.hlp.api.common.exception.custom.AuthenticationException;
import com.hlp.api.domain.user.model.User;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;

@Component
public class JwtProvider {

    private final String secretKey;
    private final Long expirationTime;

    public JwtProvider(
        @Value("${jwt.secret-key}") String secretKey,
        @Value("${jwt.access-token.expiration-time}") Long expirationTime
    ) {
        this.secretKey = secretKey;
        this.expirationTime = expirationTime;
    }

    // User
    public String createToken(@NotNull User user) {
        Key key = getSecretKey();
        return Jwts.builder()
            .signWith(key)
            .header()
            .add("typ", "JWT")
            .add("alg", key.getAlgorithm())
            .and()
            .claim("id", user.getId())
            .expiration(Date.from(Instant.now().plusMillis(expirationTime)))
            .compact();
    }

    public Integer getUserId(String token) {
        try {
            String userId = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id")
                .toString();
            return Integer.parseInt(userId);
        } catch (JwtException e) {
            throw new InvalidJwtException("유효하지 않은 JWT 토큰입니다.");
        }
    }

    // Admin
    public String createToken(@NotNull Admin admin) {
        Key key = getSecretKey();
        return Jwts.builder()
            .signWith(key)
            .header()
            .add("typ", "JWT")
            .add("alg", key.getAlgorithm())
            .and()
            .claim("id", admin.getId())
            .expiration(Date.from(Instant.now().plusMillis(expirationTime)))
            .compact();
    }

    private SecretKey getSecretKey() {
        String encoded = Base64.getEncoder().encodeToString(secretKey.getBytes());
        return Keys.hmacShaKeyFor(encoded.getBytes());
    }
}
