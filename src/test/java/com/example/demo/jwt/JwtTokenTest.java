package com.example.demo.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Function;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenTest {

    private final int TOKEN_VALIDITY = 18000;
    private final String SIGNING_KEY = "senspond";
    private String token = null;

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(subject)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                   .signWith(SignatureAlgorithm.HS256, SIGNING_KEY).compact();
    }

    private <T> T extractClaim(Claims claims, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(claims);
    }

    
    @Test
    public void test(){
        System.out.println("============ Get Token ==========");
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", "admin");
        this.token = createToken(claims,"senspond");
        System.out.println(token);
    }

    @AfterEach
    public void after(){
        System.out.println("============ From Token ==========");
        Claims claims = Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();
        
        String userName = extractClaim(claims, Claims::getSubject );
        Date date = extractClaim(claims, Claims::getExpiration );

        System.out.println(claims);
        System.out.println(userName);
        System.out.println(date);
    }
}
