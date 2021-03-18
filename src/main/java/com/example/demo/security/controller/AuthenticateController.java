package com.example.demo.security.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.security.config.CustomUserDetailService;
import com.example.demo.security.config.JwtTokenProvider;
import com.example.demo.security.service.AccountService;
import com.example.demo.security.service.dto.AuthRequestDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticateController {
    private final JwtTokenProvider jwtUtils;
    private final AuthenticationManager authenticationManager;
    
    private Logger logger = LoggerFactory.getLogger(AuthenticateController.class);
    
    private Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    private void setAuthentication(Authentication authentication){
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequestDto authRequest) throws Exception {
        
        Authentication authentication = null;

        try {

           authentication = authenticate(authRequest.getEmail(),authRequest.getPassword());
           final String token = jwtUtils.generateToken(authentication);
  // 2
           setAuthentication(authentication);
  
           logger.info("[authentication] {}", authentication);
           logger.info("Name : {}",authentication.getName());
           logger.info("Authorities : {}",authentication.getAuthorities());

           Map<String,Object> map = new HashMap<>();
           map.put("authentication", authentication);
           map.put("token", token);
           return ResponseEntity.ok().body(map);

        } catch (Exception ex) {
            // 401 : 접근 권한 없음
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("inavalid username/password");
        }

   
    }


    
}