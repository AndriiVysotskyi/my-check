package com.mycheck.auth.api;

import com.mycheck.auth.api.dto.UserDto;
import com.mycheck.auth.model.User;
import com.mycheck.auth.model.value.Email;
import com.mycheck.auth.model.value.Password;
import com.mycheck.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("users/v1")
public class AuthenticationController {

    private final UserService userService;

    @PostMapping(value = "/registration", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewUser(@RequestBody UserDto userDto) throws NoSuchAlgorithmException {
        User newUser = User.builder()
                .email(Email.of(userDto.getEmail()))
                .password(Password.of(userDto.getPassword()))
                .build();
        userService.createNewUser(newUser);
    }

    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestBody UserDto userDto) throws NoSuchAlgorithmException {
        User userForLogin = User.builder()
                .email(Email.of(userDto.getEmail()))
                .password(Password.of(userDto.getPassword()))
                .build();

        String token = userService.login(userForLogin);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping(value = "/user-data", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDto> login(@RequestHeader("Authorization") String token) {
        UserDto userData = userService.getUserData(token);
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }
}
