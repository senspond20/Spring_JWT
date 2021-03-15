package com.example.demo.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.demo.security.entity.Account;
import com.example.demo.security.repository.AccountRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService{

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if(account == null){
            throw new UsernameNotFoundException(email);
        }else{
            return new User(account.getEmail(), account.getPassword(), getAuthorities(account.getAuthority()));
        }
    }
    
    public Collection<GrantedAuthority> getAuthorities(String autority) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(autority));
        return authorities;
    }

    
}
