package com.example.demo.jwt;

import java.util.Date;

import com.example.demo.security.service.JwtTokenProviderSerivce;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;

public class JwtTokenTest {
    
    private JwtTokenProviderSerivce jwtTokenProviderSerivce;

    @BeforeEach
    public void Before(){
        jwtTokenProviderSerivce = new JwtTokenProviderSerivce();
    }

    @Test
    public void test_1(){
        String token = jwtTokenProviderSerivce.generateToken("senspond");
        System.out.println(token);
    }

    @Test
    public void test_2(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZW5zcG9uZCIsImV4cCI6MTYxNTg5MTkwNywiaWF0IjoxNjE1ODU1OTA3fQ.Kp8V8ygmRiAw_saxS6uXnys0rB_RTXmxaJotpVLCPE8";

        Claims clamis = jwtTokenProviderSerivce.extractAllClaims(token);
        String userName = jwtTokenProviderSerivce.extractUsername(token);
        Date date = jwtTokenProviderSerivce.extractExpiration(token);

        System.out.println(clamis);
        System.out.println(userName);
        System.out.println(date);
    }
}
