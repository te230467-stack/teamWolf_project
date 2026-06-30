package com.example.controller;

import com.example.model.Reserve;
import com.example.service.ReserveService;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    //Serviceを利用するための変数　
    //Controllerでserviceに処理を依頼する
    
    //コンストラクタインジェクションらしい。Autowiredはいらない子。
    private final  ReserveService reserveService;

    public AdminController(ReserveService reserveService){
        this.reserveService = reserveService;
    }



    // 管理者用：予約一覧
    @GetMapping("/admin/reserves")
    public String showReserveList(Model model) {
        //Serviceから予約一覧を取得し、htmlへ渡す
        model.addAttribute("reserves", reserveService.getAllReserves());
        return "admin-reserve-list";
    }

    // 管理者用：予約登録画面
    @GetMapping("/admin/reserves/new")
    public String showReserveForm(Model model) {
        //空のReserveオブジェクトを作成し、入力フォームを紐づける
        model.addAttribute("reserve", new Reserve());
        return "admin-reserve-form";
    }

    // 管理者用：予約登録処理
    @PostMapping("/admin/reserves")
    public String createReserve(Reserve reserve) {
        //入力された予約情報をserviceへ渡して保存
        reserveService.createReserve(reserve);
        return "redirect:/admin/reserves";
    }

    // 管理者用：予約編集画面
    @GetMapping("/admin/reserves/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Reserve reserve = reserveService.getReservesById(id).orElse(null);
        //HTMLへ渡す
        if(reserve==null){
            return "redirect:/admin/reserves";
        }
        model.addAttribute("reserve",reserve);
        return "admin-reserve-edit";
    }

    // 管理者用：予約更新
    @PostMapping("/admin/reserves/edit/{id}")
    public String updateReserve(@PathVariable Long id, Reserve reserve) {
        //IDの予約更新
        reserveService.updateReserve(id, reserve);
        return "redirect:/admin/reserves";
    }

    // 管理者用：予約削除
    @PostMapping("/admin/reserves/delete/{id}")
    public String deleteReserve(@PathVariable Long id) {
        //IDの削除
        reserveService.deleteReserve(id);
        return "redirect:/admin/reserves";
    }
}