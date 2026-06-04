package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;
 
    public String generateToken(String username) {

    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(getKey())
            .compact();
}

    private SecretKey getKey() {

    byte[] keyBytes = Decoders.BASE64.decode(secret);

    return Keys.hmacShaKeyFor(keyBytes);
}
    
}