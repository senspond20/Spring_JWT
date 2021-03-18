package com.example.demo.security.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.demo.security.entity.Account;
import com.example.demo.security.entity.Role;
import com.example.demo.security.repository.AccountRepository;
import com.example.demo.security.repository.RoleRepository;
import com.example.demo.security.service.dto.AccountSaveRequestDto;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private List<String> ADMIN_LIST = Arrays.asList(
                    "senspond@gmail.com",
                    "sdd"
            );

            
    @Override
    public Account saveAccount(AccountSaveRequestDto accountSaveRequestDto){

        String  bcryptPassword = passwordEncoder.encode(accountSaveRequestDto.getPassword());
        Account account = accountSaveRequestDto.toEntity(bcryptPassword);

        Role role = roleRepository.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        
        if(ADMIN_LIST.contains(account.getEmail())){
        // if(account.getEmail().equals("senspond@gmail.com")){
            role = roleRepository.findByName("ADMIN");
            roleSet.add(role);
        }

        account.updateRoles(roleSet);
        return accountRepository.save(account);
    }
//  @RolesAllowed("ROLE_ADMIN")
    
    @Override
    public List<Account> getAccountAll(){
        return accountRepository.findAll();
    }


 /*
    public Collection<GrantedAuthority> getAuthorities(String autority) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(autority));
        return authorities;
    }
*/
    
}
