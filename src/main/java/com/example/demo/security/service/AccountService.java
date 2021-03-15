package com.example.demo.security.service;

import java.util.List;

import com.example.demo.security.entity.Account;
import com.example.demo.security.repository.AccountRepository;
import com.example.demo.security.service.dto.AccountSaveRequestDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    
    private  final PasswordEncoder passwordEncoder;
    
    public Account saveAccount(AccountSaveRequestDto accountSaveRequestDto) throws Exception{
        String  bcryptPassword = passwordEncoder.encode(accountSaveRequestDto.getPassword());
        return accountRepository.save(accountSaveRequestDto.toEntity(bcryptPassword));
    }

    public List<Account> getAccountAll(){
        return accountRepository.findAll();
    }
}
