package com.example.demo.security.controller;

import java.util.List;

import com.example.demo.security.entity.Account;
import com.example.demo.security.service.AccountService;
import com.example.demo.security.service.JwtTokenProviderSerivce;
import com.example.demo.security.service.dto.AccountSaveRequestDto;
import com.example.demo.security.service.dto.AuthRequestDto;
import com.example.demo.security.service.dto.AuthReseponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;


    // @GetMapping("/")
    // public String welcome() {
    //   return "안녕하세요 !!";
    // }
    
    // @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/account/all")
    public List<Account> getAccountAll(){
        return accountService.getAccountAll();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> insertAccount(@RequestBody AccountSaveRequestDto accountSaveRequestDto){
        Account account = null;
        try {
            account = accountService.saveAccount(accountSaveRequestDto);
            return ResponseEntity.ok().body(account);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("msg : 이미 존재하는 이메일입니다. : " + accountSaveRequestDto.getEmail());
        }
    }

}
