package com.example.demo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.security.service.CustomUserDetailService;
import com.example.demo.security.service.JwtTokenProviderSerivce;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

@Autowired
private JwtTokenProviderSerivce jwtService;

@Autowired
private CustomUserDetailService cuService;

@Override
protected void doFilterInternal(HttpServletRequest httpServletRequest, 
                                HttpServletResponse httpServletResponse, 
                                FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String token = null;
        String userName = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            userName = jwtService.extractUsername(token);
        }
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
             UserDetails userDetails = cuService.loadUserByUsername(userName);
        if (jwtService.validateToken(token, userDetails)) {
             UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
             new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken
        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
}

