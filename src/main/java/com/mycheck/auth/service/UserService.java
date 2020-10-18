package com.mycheck.auth.service;

import com.mycheck.auth.api.dto.UserDto;
import com.mycheck.auth.dao.PasswordAndSalt;
import com.mycheck.auth.dao.TokenDao;
import com.mycheck.auth.dao.UserDao;
import com.mycheck.auth.model.User;
import com.mycheck.auth.repository.TokenRepository;
import com.mycheck.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public void createNewUser(User user) throws NoSuchAlgorithmException {
        String salt = RandomStringUtils.random(32);

        byte[] bytes = getHashedAndSaltedPassword(user.getPassword().getValue(), salt);

        userRepository.save(UserDao.ofUserHashedPasswordAndSalt(user, new String(bytes, UTF_8), salt));
    }

    private byte[] getHashedAndSaltedPassword(char[] password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt.getBytes());

        return md.digest(new String(password).getBytes(UTF_8));
    }

    public String login(User userForLogin) throws NoSuchAlgorithmException {
        Optional<UserDao> userDao = userRepository.findById(userForLogin.getEmail().getValue());

        verifyPassword(userDao, userForLogin);

        TokenDao tokenDao = tokenRepository.save(TokenDao.of(userDao.orElseThrow(NoSuchElementException::new).getEmail()));
        return tokenDao.getToken();
    }

    private void verifyPassword(Optional<UserDao> userDao, User user) throws NoSuchAlgorithmException {
        PasswordAndSalt passwordAndSalt = userDao.orElseThrow(NoSuchElementException::new).getPasswordAndSalt();
        char[] providedPassword = user.getPassword().getValue();
        String salt = passwordAndSalt.getSalt();
        byte[] hashedAndSaltedPasswordBytes = getHashedAndSaltedPassword(providedPassword, salt);

        String hashedAndSaltedPasswordProvided = new String(hashedAndSaltedPasswordBytes, UTF_8);

        String hashedPassword = passwordAndSalt.getHashedPassword();

        if (!hashedAndSaltedPasswordProvided.equals(hashedPassword)) {
            throw new SecurityException("wrong password");
        }

    }

    public UserDto getUserData(String token) {
        Optional<TokenDao> userToken = tokenRepository.findById(token);
        Optional<UserDao> user = userRepository.findById(userToken.orElseThrow(NoSuchElementException::new).getUserId());
        String userEmail = user.orElseThrow(NoSuchElementException::new).getEmail();
        return UserDto.of(userEmail);
    }
}
