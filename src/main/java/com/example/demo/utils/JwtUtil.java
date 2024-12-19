package com.example.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret_key}")
    private String SECRET_KEY;
    public final long EXPIRATION_TIME = 24 * 3600000;

    public static final String TOKEN_NAME = "foundit_jwt";

    public String generateToken(String email, String password) {
        return Jwts.builder()
                .setSubject(email)
                .claim("password", password)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims validateToken(String token) {
        JwtParser parser = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build();

        try {
            return parser.parseSignedClaims(token).getPayload();
        }
        catch (Exception e) {
            return null;
        }
    }

    public String getEmailFromToken(String token) {
        Claims claims = validateToken(token);
        return claims != null ? claims.getSubject() : null;
    }

    public String getPasswordFromToken(String token) {
        Claims claims = validateToken(token);
        return claims != null ? (String) claims.get("password") : null;
    }

    public boolean isTokenExpired(String token) {
        Claims claims = validateToken(token);
        return claims != null && claims.getExpiration().before(new Date());
    }

    public long getExpirationTime() {
        return EXPIRATION_TIME;
    }

    public String getTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (TOKEN_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
