package com.mycheck.auth.model.value;

import lombok.Getter;

@Getter
public class Password {
    char[] value;

    private Password(char[] value) {
        this.value = value;
    }

    public static Password of(char[] value) {
        //TODO add password validation (strength)
        return new Password(value);
    }
}
