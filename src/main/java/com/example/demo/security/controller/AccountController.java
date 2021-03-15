package com.example.demo.security.controller;

import java.util.List;

import com.example.demo.security.entity.Account;
import com.example.demo.security.service.AccountService;
import com.example.demo.security.service.JwtTokenProviderSerivce;
import com.example.demo.security.service.dto.AccountSaveRequestDto;
import com.example.demo.security.service.dto.AuthRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtTokenProviderSerivce jwtTokenProviderSerivce;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String welcome() {
      return "안녕하세요 !!";
    }
    
    @GetMapping("/account/all")
    public List<Account> getAccountAll(){
        return accountService.getAccountAll();
    }
    @PostMapping("/account/save")
    public Account insertAccount(@RequestBody AccountSaveRequestDto accountSaveRequestDto){
        return accountService.saveAccount(accountSaveRequestDto);
    }


    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequestDto authRequest) throws Exception {
        try {
        authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        } catch (Exception ex) {
        throw new Exception("inavalid username/password");
        }
         return jwtTokenProviderSerivce.generateToken(authRequest.getEmail());
    }



    

}
