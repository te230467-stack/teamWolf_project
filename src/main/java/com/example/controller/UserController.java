package com.example.controller;

import com.example.form.ReserveForm;
import com.example.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    //ReservationSeriveのSpringを生成
    //ReservationServiceのインスタンスを生成
    //UserCOntrollerのコンストラクタに渡す
    private final ReservationService reservationService;

    public UserController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 予約入力画面を表示する処理
    @GetMapping("/reserve")
    public String showReserveForm() {
        return "reserve-form";
    }

    // 予約内容を送信する処理
    @PostMapping("/reserve")
    public String reserveBook(ReserveForm form, Model model) {

        if (form.getBookName() == null || form.getBookName().isBlank()) {
            model.addAttribute("error", "書名を入力してください");
            return "reserve-form";
        }

        reservationService.reserve(form);

        return "reserve-complete";
    }

    // 予約内容の編集画面を表示する処理
    @GetMapping("/reserve/edit/{id}")
    public String showEditForm(@RequestParam Long id, Model model) {
        // IDから予約情報を取得
        // modelに入れて編集画面へ渡す
        return "reserve-edit";
    }

    // 予約内容を更新する処理
    @PostMapping("/reserve/edit/{id}")
    public String updateReserve(@RequestParam Long id, ReserveForm form) {
        // Serviceに更新処理を依頼
        // reservationService.update(id, form);
        return "redirect:/reserve/complete";
    }
}