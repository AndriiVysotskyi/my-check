package com.mycheck.auth.dao;

import com.mycheck.auth.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "USERS")
@NoArgsConstructor
public class UserDao {

    @Id
    @Column(columnDefinition = "varchar(100)")
    private String email;

    @Embedded
    PasswordAndSalt passwordAndSalt;

    private UserDao(String email, String password, String salt) {
        this.email = email;
        this.passwordAndSalt = new PasswordAndSalt(password, salt);
    }

    public static UserDao ofUserHashedPasswordAndSalt(User user, String password, String salt) {
        return new UserDao(user.getEmail().getValue(), password, salt);
    }
}
