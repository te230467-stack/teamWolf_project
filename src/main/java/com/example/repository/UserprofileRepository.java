package com.example.repository;

import com.example.model.Userprofile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserprofileRepository extends JpaRepository<Userprofile, Long> {

    // user_id（=Userprofileのid、@MapsId）でプロフィール取得
    Optional<Userprofile> findByUser_Id(Long userId);

    // メールアドレスで検索（重複確認など）
    Optional<Userprofile> findByEmail(String email);

    boolean existsByEmail(String email);
}
