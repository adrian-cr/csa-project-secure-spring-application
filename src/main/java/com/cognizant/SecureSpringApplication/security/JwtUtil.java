package com.cognizant.SecureSpringApplication.security;

import com.cognizant.SecureSpringApplication.services.UserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private final String SECRET = "j9H$x2P#mK8nL5vR4qW7tY0cF3bE6aD1iO9uZ@xN4wM7sA2pQ5yB8hJ$kG6";
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 72; //72 hours
    private final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    @Autowired private UserDetailsService uds;


    public String generateToken(String username) {
        UserDetails userDetails = uds.loadUserByUsername(username);
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("authorities", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(KEY)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
