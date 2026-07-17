package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.model.Userprofile;
import com.example.repository.UserprofileRepository;

@Service
public class UserprofileService {
    @Autowired
    private UserprofileRepository userRepository;

    public Userprofile createUserprofile(Userprofile user_proifle) {

        return userRepository.save(user_proifle);

    }

}
