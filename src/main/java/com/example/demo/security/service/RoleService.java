package com.example.demo.security.service;

import com.example.demo.security.entity.Role;

public interface RoleService {
    Role findByName(String name);
}
