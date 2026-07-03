package com.example.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    // 主キー
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ユーザー名（重複不可）
    @Column(unique = true, nullable = false)
    private String username;

    // パスワード
    @Column(nullable = false)
    private String password;

    // 権限（ROLE_USER, ROLE_ADMINなど）
    @Column(nullable = false)
    private String role;

    // JPAが使用するためのデフォルトコンストラクタ
    public User() {
    }

    // 新規ユーザー登録時に使用するコンストラクタ
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // IDを取得する
    public Long getId() {
        return id;
    }

    // 権限を取得する
    public String getRole() {
        return role;
    }

    // Spring Securityへユーザーの権限を渡す
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role);
    }

    // パスワードを返す
    @Override
    public String getPassword() {
        return password;
    }

    // ユーザー名を返す
    @Override
    public String getUsername() {
        return username;
    }

    // アカウントの有効期限が切れていないか
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // アカウントがロックされていないか
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // パスワードの有効期限が切れていないか
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // ユーザーが有効かどうか
    @Override
    public boolean isEnabled() {
        return true;
    }
}