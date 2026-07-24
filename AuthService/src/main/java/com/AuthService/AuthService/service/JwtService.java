package com.AuthService.AuthService.service;

import com.AuthService.AuthService.model.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public String generateAccessToken(AuthUser user) {

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole().name())
                .claim("tokenType", "ACCESS")
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + accessTokenExpiration
                        )
                )
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(AuthUser user) {

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("tokenType", "REFRESH")
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + refreshTokenExpiration
                        )
                )
                .signWith(getSigningKey())
                .compact();
    }

    public String extractEmail(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUserId(String token) {

        Number userId = extractAllClaims(token)
                .get("userId", Number.class);

        return userId.longValue();
    }

    public String extractRole(String token) {
        return extractAllClaims(token)
                .get("role", String.class);
    }

    public String extractTokenType(String token) {
        return extractAllClaims(token)
                .get("tokenType", String.class);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenValid(
            String token,
            AuthUser user
    ) {

        try {
            String email = extractEmail(token);

            return email.equals(user.getEmail())
                    && !isTokenExpired(token)
                    && "ACCESS".equals(extractTokenType(token));

        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    public boolean isRefreshTokenValid(String token) {

        try {
            return !isTokenExpired(token)
                    && "REFRESH".equals(extractTokenType(token));

        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token)
                .before(new Date());
    }

    private <T> T extractClaim(
            String token,
            Function<Claims, T> claimResolver
    ) {

        Claims claims = extractAllClaims(token);

        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }
}