package com.example.demo.jwt;

import java.util.Date;

import com.example.demo.security.config.JwtTokenProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;

public class JwtTokenTest {
    
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void Before(){
        jwtTokenProvider = new JwtTokenProvider();
    }

    @Test
    public void test_1(){
        String token = jwtTokenProvider.generateToken("senspond");
        System.out.println(token);
    }

    @Test
    public void test_2(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZW5zcG9uZCIsImV4cCI6MTYxNTg5MTkwNywiaWF0IjoxNjE1ODU1OTA3fQ.Kp8V8ygmRiAw_saxS6uXnys0rB_RTXmxaJotpVLCPE8";

        Claims clamis = jwtTokenProvider.extractAllClaims(token);
        String userName = jwtTokenProvider.extractUsername(token);
        Date date = jwtTokenProvider.extractExpiration(token);

        System.out.println(clamis);
        System.out.println(userName);
        System.out.println(date);
    }
}
