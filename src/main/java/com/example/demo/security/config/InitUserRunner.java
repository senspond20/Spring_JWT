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

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
    
        // Role 추가
        roleRepository.save(Role.builder().name("ADMIN").description("관리자 권한").build());
        roleRepository.save(Role.builder().name("USER").description("사용자 권한").build());

        System.out.println("=============[ 권한 목록 ]=====================");
        roleRepository.findAll().stream().forEach(i-> System.out.println(i.getName()));
        System.out.println("==================================");
        System.out.println(roleRepository.findByName("USER"));
        
    }
    
}
