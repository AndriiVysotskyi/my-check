package com.mycheck.auth.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    char[] password;

    private UserDto(String email) {
        this.email = email;
    }

    public static UserDto of(String userEmail) {
        return new UserDto(userEmail);
    }
}
