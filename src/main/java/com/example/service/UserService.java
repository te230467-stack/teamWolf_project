package com.example.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.Reserve;
import com.example.model.User;
import com.example.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // コンストラクタインジェクション
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    // ユーザー登録
    public User createUser(User user) {

        // ユーザー名の重複確認
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException(
                    "このユーザー名はすでに使用されています");
        }

        // 入力されたパスワードを暗号化
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        user.setRole("ROLE_USER");

        return userRepository.save(user);
    }

    // IDからユーザー取得
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // ログイン処理
    public Optional<User> login(
            String username,
            String rawPassword) {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();

        // 入力されたパスワードと暗号化済みパスワードを照合
        if (!passwordEncoder.matches(
                rawPassword,
                user.getPassword())) {

            return Optional.empty();
        }

        return Optional.of(user);
    }

    // 登録情報更新
    public User updateUser(
            Long id,
            User updatedUser) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ユーザーが見つかりません"));

        // 同じユーザー名を別の人が使用していないか確認
        Optional<User> sameUsernameUser = userRepository.findByUsername(
                updatedUser.getUsername());

        if (sameUsernameUser.isPresent()
                && !sameUsernameUser.get()
                        .getId()
                        .equals(id)) {

            throw new IllegalArgumentException(
                    "このユーザー名はすでに使用されています");
        }

        existingUser.setUsername(
                updatedUser.getUsername());

        // 新しいパスワードが入力された場合だけ変更
        if (updatedUser.getPassword() != null
                && !updatedUser.getPassword().isBlank()) {

            existingUser.setPassword(
                    passwordEncoder.encode(
                            updatedUser.getPassword()));
        }

        /*
         * 一般ユーザー自身にroleを変更させない場合は、
         * roleは更新しない。
         */

        return userRepository.save(existingUser);
    }

    // ユーザー削除
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "削除するユーザーが見つかりません");
        }

        userRepository.deleteById(id);
    }
}