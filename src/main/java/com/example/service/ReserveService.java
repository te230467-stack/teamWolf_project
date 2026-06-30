package com.example.service;

import com.example.model.Reserve;
import com.example.repository.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class ReserveService {
    @Autowired
    private ReserveRepository reserverepository;

    //すべての書籍を取得
    public List<Reserve> getAllReserves(){
        return reserverepository.findAll();
    }
    //1件書籍取得
    public Optional<Reserve> getReservesById(Long id){
        return reserverepository.findById(id);
    }
    // 書籍登録
    public Reserve createReserve(Reserve reserve){
        return reserverepository.save(reserve);
    }
    //更新
    public Reserve updateReserve(Long id, Reserve reserveDetails) {

        Reserve reserve = reserverepository.findById(id).orElseThrow(() -> new IllegalArgumentException("予約が見つかりません。"));
        
        reserve.setReserve_id(reserveDetails.getReserve_id());
        reserve.setTitle(reserveDetails.getTitle());
        reserve.setAuthor(reserveDetails.getAuthor());
        reserve.setPublisher(reserveDetails.getPublisher());

        return reserverepository.save(reserve);
    }
    //削除
    public void deleteReserve(Long id){
        Reserve reserve = reserverepository.findById(id).orElseThrow(()->new IllegalArgumentException("書籍が見つかりません"));
        reserverepository.delete(reserve);
    }
}