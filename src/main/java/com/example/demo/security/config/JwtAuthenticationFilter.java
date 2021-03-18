package com.example.demo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

     private final String HEADER_STRING ="Authorization";
     private final String TOKEN_PREFIX = "Bearer ";
     
     @Autowired
     private JwtTokenProvider jwtUtils;

     @Autowired
     private CustomUserDetailService userDetailService;

     @Override
     protected void doFilterInternal(HttpServletRequest httpServletRequest, 
                                HttpServletResponse httpServletResponse, 
                                FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader(HEADER_STRING);
        
        logger.info("===================================");
        logger.info(authorizationHeader);
        logger.info("===================================");


        String authToken = null;
        String userName = null;
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            authToken = authorizationHeader.replace(TOKEN_PREFIX,"");
           
            logger.info("===================================");
            logger.info(authToken);
            logger.info("===================================");
        //    authToken = authorizationHeader.substring(7);
        //    authToken = authorizationHeader.replace(TOKEN_PREFIX, "");
         
           try {
               userName = jwtUtils.extractUsername(authToken);

               System.out.println(userName);

           } catch (IllegalArgumentException e) {
               logger.error("An error occurred while fetching Username from Token", e);
           } catch (ExpiredJwtException e) {
               logger.warn("The token has expired", e);
           } catch(SignatureException e){
               logger.error("Authentication Failed. Username or Password not valid.");
           }
        }else {
          logger.warn("Couldn't find bearer string, header will be ignored");
        }
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

             UserDetails userDetails = userDetailService.loadUserByUsername(userName);
             
             if (jwtUtils.validateToken(authToken, userDetails)) {
        
                System.out.println("========getAuthorities===============");
                System.out.println(userDetails.getAuthorities());
                System.out.println("=======================");
                UsernamePasswordAuthenticationToken authentication = 
            //   jwtUtils.getAuthenticationToken(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
        
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
               SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

