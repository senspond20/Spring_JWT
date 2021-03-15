package com.example.demo.security.service.dto;

import com.example.demo.security.entity.Account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountSaveRequestDto {
    private String email;
    private String password;
    private String authority = "USER"; // default

    @Builder
    public AccountSaveRequestDto(String email, String password, String authority){
        super();
        this.email = email;
        this.password = password;
        this.authority = authority;
    }

    public Account toEntity(String bcryptPassword){
        return Account.builder()
                      .email(email)
                      .password(bcryptPassword)
                      .authority(authority)
                      .build();
    }

}
