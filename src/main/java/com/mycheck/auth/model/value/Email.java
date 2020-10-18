package com.mycheck.auth.model.value;

import lombok.Getter;

@Getter
public class Email {
    private final String value;

    private Email(String value) {
        this.value = value;
    }

    public static Email of(String value) {
        //TODO add email validation
        return new Email(value);
    }
}
