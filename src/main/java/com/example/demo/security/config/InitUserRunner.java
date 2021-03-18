package com.example.demo.security.config;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.security.entity.Account;
import com.example.demo.security.entity.Role;
import com.example.demo.security.repository.AccountRepository;
import com.example.demo.security.repository.RoleRepository;
import com.example.demo.security.service.AccountService;
import com.example.demo.security.service.dto.AccountSaveRequestDto;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class InitUserRunner implements CommandLineRunner{

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final RoleRepository roleRepository;
    
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
    
        // Role 추가
        roleRepository.save(Role.builder().name("ADMIN").description("관리자 권한").build());
        roleRepository.save(Role.builder().name("USER").description("사용자 권한").build());

        System.out.println("==================================");
        roleRepository.findAll().stream().forEach(i-> System.out.println(i.getName()));
        System.out.println("==================================");
        System.out.println(roleRepository.findByName("USER"));

        // Role role = roleRepository.findByName("USER");
        // Set<Role> roleSet = new HashSet<>();
        // roleSet.add(role);
        
        // accountRepository.save(
        //     Account.builder().email("senspond@gmail.com")
        //                      .password(passwordEncoder.encode("1234"))
        //                      .roles(roleSet).build());

        //Account 추가
        // AccountSaveRequestDto dto = AccountSaveRequestDto.builder().email("senspond@gmail.com")
        //                          .password("1234").build();
        //                         //  .authority("ADMIN").build();

        // System.out.println("============================");
        // System.out.println(accountService.saveAccount(dto));
        // System.out.println("============================");

        // dto = AccountSaveRequestDto.builder().email("guest@gmail.com")
        // .password("1234").build();
        // // .authority("USER").build();

        // System.out.println("============================");
        // System.out.println(accountService.saveAccount(dto));
        // System.out.println("============================");

        // TODO Auto-generated method stub
        
    }
    
}
