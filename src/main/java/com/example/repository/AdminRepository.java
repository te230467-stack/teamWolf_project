package com.example.repository;

import com.example.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // ログインID(username)で管理者を検索
    Optional<Admin> findByUsername(String username);

    // 存在チェック（登録時の重複確認用）
    boolean existsByUsername(String username);
}