package com.example.controller;

import com.example.model.Reserve;
import com.example.service.ReserveService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    // Serviceを使うための変数
    // ControllerはDB処理を直接せず、Serviceに処理を依頼する
    private final ReserveService reserveService;

    // コンストラクタインジェクション
    // SpringがReserveServiceを自動で渡してくれる
    public UserController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    // 予約入力画面を表示する
    @GetMapping("/reserve")
    public String showReserveForm(Model model) {

        // 入力フォームと紐付けるため、空のReserveをHTMLへ渡す
        model.addAttribute("reserve", new Reserve());

        return "reserve";
    }

    // 予約登録処理
    @PostMapping("/reserve")
    public String reserveBook(Reserve reserve, Model model) {

        // タイトルが未入力なら、エラーメッセージを表示して入力画面へ戻す
        //if (reserve.getTitle() == null || reserve.getTitle().isBlank()) {
            //model.addAttribute("error", "書名を入力してください");
            //return "reserve";
        //}

        // 入力された予約情報をServiceへ渡して保存する
        reserveService.createReserve(reserve);

        // 登録後は予約完了画面へ移動する←これは嘘
        return "redirect:/reserve";
    }

    // 予約編集画面を表示する
    @GetMapping("/reserve/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {

        // URLの{id}を使って、編集対象の予約情報を取得する
        Reserve reserve = reserveService.getReserveById(id).orElse(null);

        // 存在しないIDなら一覧画面へ戻す
        if (reserve == null) {
            return "redirect:/reserve/list";
        }
        // 取得した予約情報をHTMLへ渡す
        model.addAttribute("reserve", reserve);

        return "edit";
    }

    // 予約更新処理
    @PostMapping("/reserve/edit/{id}")
    public String updateReserve(@PathVariable Long id, Reserve reserve) {

        // 指定したIDの予約情報を、入力内容で更新する
        reserveService.updateReserve(id, reserve);

        return "redirect:/reserve";
    }

    // 予約一覧画面を表示する
    @GetMapping("/reserve/list")
    public String showReserveList(Model model) {

        // Serviceから全予約を取得してHTMLへ渡す
        model.addAttribute("reserves", reserveService.getAllReserves());

        return "reservelist";
    }

    

    // 予約削除処理
    @PostMapping("/reserve/delete/{id}")
    public String deleteReserve(@PathVariable Long id) {

        // 指定したIDの予約情報を削除する
        reserveService.deleteReserve(id);

        return "redirect:/reserve/list";
    }
}