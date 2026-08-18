package com.nicolas.revenda.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String gerarToken(String email, String role) {
        long agora = System.currentTimeMillis();
        long expiracao = agora + expiration;

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date(agora))
                .expiration(new Date(expiracao))
                .signWith(getSigningKey())
                .compact();
    }

    public String extrairEmail(String token) {
        return extrairTodasClaims(token).getSubject();
    }

    public boolean tokenValido(String token) {
        try {
            extrairTodasClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extrairTodasClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
