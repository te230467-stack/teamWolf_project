package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    // ログイン画面を表示する
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // アカウント作成画面を表示する
    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        model.addAttribute("user", new User());

        return "register";
    }

    // アカウント作成処理
    @PostMapping("/register")
    public String registerUser(User user) {

        userService.createUser(user);

        return "redirect:/login";
    }

    // アカウント削除確認画面を表示する
    @GetMapping("/account/delete/{id}")
    public String showDeleteForm(
            @PathVariable Long id,
            Model model) {

        User user = userService.getUserById(id).orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        return "account-delete";
    }

    // アカウント削除処理
    @PostMapping("/account/delete/{id}")
    public String deleteAccount(@PathVariable Long id) {

        userService.deleteUser(id);

        return "redirect:/login";
    }
}