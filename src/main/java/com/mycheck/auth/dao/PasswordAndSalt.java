package com.mycheck.auth.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PasswordAndSalt {

    @Column(columnDefinition = "varchar(200)")
    String hashedPassword;

    @Column(columnDefinition = "varchar(50)")
    String salt;
}
