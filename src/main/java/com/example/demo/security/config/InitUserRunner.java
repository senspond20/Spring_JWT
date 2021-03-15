package com.example.demo.security.config;

import com.example.demo.security.entity.Account;
import com.example.demo.security.repository.AccountRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class InitUserRunner implements CommandLineRunner{

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        
        Account account = Account.builder().email("senspond@gmail.com")
                                 .password(passwordEncoder.encode("1234"))
                                 .authority("ADMIN").build();

        System.out.println("============================");
        System.out.println(accountRepository.save(account));
        System.out.println("============================");

        // TODO Auto-generated method stub
        
    }
    
}
