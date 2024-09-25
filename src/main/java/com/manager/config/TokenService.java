package com.manager.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class TokenService {

    @Value("${security.jwt.secret-key}")
    private String JWT_SECRET;
    @Value("${security.jwt.expiration-time}")
    private long JWT_EXPIRATION;

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("Caught exception in isTokenValid in TokenService: " + e.getMessage());
            return false;
        }
    }

    public String getUserEmailFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
        return claims.get("username", String.class);
    }

    public Map<String, String> getPayloadFromToken(String token){
        Map<String, String> payload = new HashMap<>();
        Claims claims = Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
        String username = claims.get("username", String.class);
        String email = claims.getSubject();
        payload.put("username", username);
        payload.put("email", email);
        return payload;
    }

    public String generateJwtToken(String email, String username) {
        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
            .setSubject(email)
            .claim("email", email)
            .claim("username", username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(new Date().getTime() + JWT_EXPIRATION))
            .signWith(key)
            .compact();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7).strip();
        }
        return null;
    }

    public String getUserEmailFromHeader(HttpServletRequest request){
        return getUserEmailFromToken(getTokenFromRequest(request));
    }
}
