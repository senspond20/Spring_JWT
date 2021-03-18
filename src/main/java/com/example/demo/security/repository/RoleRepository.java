package com.example.demo.security.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import com.example.demo.security.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
   
   Role findByName(String name);
  
   List<Role> findAll();
}
