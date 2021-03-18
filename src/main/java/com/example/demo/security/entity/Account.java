package com.example.demo.security.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import org.hibernate.annotations.DynamicInsert;

import javax.persistence.JoinColumn;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = "id")
@Table(name = "ACCOUNT")
@NoArgsConstructor
@ToString
@Entity
@DynamicInsert
public class Account {
    @Id
    @Column(name = "Account_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name ="PASSWORD", length = 120, nullable = false)
    private String password;

    // @ColumnDefault("'USER'")
    // @Column(name ="ROLE", length = 15, nullable = false)

    // private String authority;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Account_ROLES",
            joinColumns = {
            @JoinColumn(name = "Account_ID")
            },
            inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles;

    
    @Builder
    public Account(String email, String password, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }


    public void updateRoles(Set<Role> roleSet) {
        this.roles = roleSet;
    }
}
