package com.example.demo.security.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.example.demo.security.service.CustomUserDetailService;
import com.example.demo.security.service.JwtTokenProviderSerivce;
import com.example.demo.security.service.dto.AuthRequestDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticateController {
    private final JwtTokenProviderSerivce jwtTokenProviderSerivce;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService userDetailsService;
    
    @PostMapping("/authenticate")
    public Map<String,Object> generateToken(@RequestBody AuthRequestDto authRequest) throws Exception {

        authenticate(authRequest.getEmail(), authRequest.getPassword());
        UserDetails userDetails = null;
        try {
            userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());   
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
            String token = jwtTokenProviderSerivce.generateToken(authRequest.getEmail());    
            
        Map<String,Object> map = new HashMap<>();
        map.put("userDetail", userDetails);
        map.put("token", token);
        return map;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    
}