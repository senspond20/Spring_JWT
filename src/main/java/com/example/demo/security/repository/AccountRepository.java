package com.example.demo.security.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.security.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    Account findByEmail(String email);
    
}
