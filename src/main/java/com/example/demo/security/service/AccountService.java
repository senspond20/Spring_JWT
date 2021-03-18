package com.example.demo.security.service;

import java.util.List;

import com.example.demo.security.entity.Account;
import com.example.demo.security.service.dto.AccountSaveRequestDto;



public interface AccountService {

    Account saveAccount(AccountSaveRequestDto accountSaveRequestDto);
    
    List<Account> getAccountAll();

}
