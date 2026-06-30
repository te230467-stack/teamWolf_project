package com.example.repository;

import com.example.model.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    // ---- CREATE ----
    // JpaRepositoryのsave()をそのまま使う
    // reserveRepository.save(reserve);

    // ---- READ（補助） ----
    Optional<Reserve> findByReserveId(String reserveId);

    List<Reserve> findByPhoneNumber(String phoneNumber);

    // 重複予約チェック（BI010：同じ電話番号・同じ書名）
    Optional<Reserve> findByPhoneNumberAndTitle(String phoneNumber, String title);

    // ---- UPDATE ----
    // 発注状態の更新（BI004）
    @Modifying
    @Transactional
    @Query("UPDATE Reserve r SET r.orderStatus = :orderStatus WHERE r.id = :id")
    int updateOrderStatus(@Param("id") Long id, @Param("orderStatus") String orderStatus);


    // 予約内容の編集（BI003）
    @Modifying
    @Transactional
    @Query("UPDATE Reserve r SET r.title = :title, r.author = :author, r.publisher = :publisher WHERE r.id = :id")
    int updateReserveContent(@Param("id") Long id,
                              @Param("title") String title,
                              @Param("author") String author,
                              @Param("publisher") String publisher);

    // ---- DELETE ----
    // JpaRepositoryのdeleteById()をそのまま使用
    // reserveRepository.deleteById(id);
}
