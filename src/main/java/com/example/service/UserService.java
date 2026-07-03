package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String password, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("ユーザー名は既に使用されています");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(username, encodedPassword, role);
        userRepository.save(user);
    }
}