package com.mycheck.auth.model;

import com.mycheck.auth.model.value.Email;
import com.mycheck.auth.model.value.Password;
import lombok.Builder;
import lombok.Getter;

@Builder
public class User {

    @Getter
    Email email;

    @Getter
    Password password;
}
