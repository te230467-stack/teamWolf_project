package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // データベースを操作
    @Autowired
    private UserRepository userRepository;
    //パスワードを暗号化
    @Autowired
    private PasswordEncoder passwordEncoder;

    //public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    // public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
    //     this.userRepository = userRepository;
    //     this.passwordEncoder = passwordEncoder;
    // }

    //ユーザー登録
    public void registerUser(String username, String password, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("ユーザー名は既に使用されています");
        }

        String encodedPassword = passwordEncoder.encode(password);
        
        User user = new User(username, password, role);
        //User user = new User(username, encodedPassword, role);
        userRepository.save(user);
    }
    
    //プロフィール更新
    public void updateUser(Long id,String username,String password){
        User user = findById(id);
        user.setUsername(username);
        if (password != null && !password.isBlank()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        userRepository.save(user);
    }

    // ユーザー削除
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}