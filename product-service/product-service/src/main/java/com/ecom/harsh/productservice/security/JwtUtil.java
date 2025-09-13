package com.ecom.harsh.productservice.security;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {  
    private final Key key;
    private final String secret;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.secret = secret;
    }

    String getUsername(String token) {
        return extractClaims(token).getSubject();
    }

    String extract(String token, String claim) {
        return (String) extractClaims(token).get(claim);
    }

    Boolean validateTokenAndExpiry(String token) {
        return extractClaims(token).getExpiration().before(new java.util.Date());
    }


    private Claims extractClaims(String token) {
        System.out.println("token: " + token);
        System.out.println("key: " + key);

        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        // return Jwts.parserBuilder()
        //         .setSigningKey(key)
        //         .build()
        //         .parseClaimsJws(token)
        //         .getBody();
    }

}
