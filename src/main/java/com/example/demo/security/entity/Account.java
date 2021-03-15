package com.example.demo.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name ="PASSWORD", length = 120, nullable = false)
    private String password;

    private String authority;

    @Builder
    public Account(String email, String password, String authority) {

        this.email = email;
        this.password = password;
        this.authority = authority;
    }
}
