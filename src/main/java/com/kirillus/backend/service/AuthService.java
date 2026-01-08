package com.kirillus.backend.service;

import com.kirillus.backend.dto.LoginRequest;
import com.kirillus.backend.dto.LoginResponse;
import com.kirillus.backend.entity.User;
import com.kirillus.backend.repository.UserRepository;
import com.kirillus.backend.security.TokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Неверное имя пользователя или пароль");
        }

        User user = userOpt.get();
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Неверное имя пользователя или пароль");
        }

        String token = TokenStore.generateToken(user.getId());

        LoginResponse response = new LoginResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setLastName(user.getLastName());
        response.setFirstName(user.getFirstName());
        response.setMiddleName(user.getMiddleName());
        response.setRoleName(user.getRole().getName());
        response.setToken(token);

        return response;
    }
}

