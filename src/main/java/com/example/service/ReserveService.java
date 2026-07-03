package com.example.service;

import com.example.model.Reserve;
import com.example.repository.ReserveRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReserveService {

    // ReserveRepositoryを使ってDB操作を行う
    private final ReserveRepository reserveRepository;

    // コンストラクタインジェクション
    // Springが自動でReserveRepositoryを渡してくれる
    public ReserveService(ReserveRepository reserveRepository) {
        this.reserveRepository = reserveRepository;
    }

    // すべての予約情報を取得する
    public List<Reserve> getAllReserves() {
        return reserveRepository.findAll();
    }

    // IDを指定して1件の予約情報を取得する
    public Optional<Reserve> getReservesById(Long id) {
        return reserveRepository.findById(id);
    }

    // 予約情報を登録する
    public Reserve createReserve(Reserve reserve) {
        return reserveRepository.save(reserve);
    }

    // 予約情報を更新する
    public Reserve updateReserve(Long id, Reserve reserveDetails) {

        // 指定されたIDの予約がなければエラーを出す
        Reserve reserve = reserveRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("予約が見つかりません。"));

        // 入力された内容で既存データを更新する
        reserve.setReserve_id(reserveDetails.getReserve_id());
        reserve.setTitle(reserveDetails.getTitle());
        reserve.setAuthor(reserveDetails.getAuthor());
        reserve.setPublisher(reserveDetails.getPublisher());

        // 更新した内容を保存する
        return reserveRepository.save(reserve);
    }

    // 予約情報を削除する
    public void deleteReserve(Long id) {

        // 指定されたIDの予約がなければエラーを出す
        Reserve reserve = reserveRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("予約が見つかりません。"));

        // 予約情報を削除する
        reserveRepository.delete(reserve);
    }
}