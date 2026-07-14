package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    // ログイン画面
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // ログイン処理
    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        User user = userService.login(username, password).orElse(null);

        if (user == null) {
            model.addAttribute("error", "ユーザー名またはパスワードが違います");
            return "login";
        }

        // ログインした利用者をセッションに保存
        session.setAttribute("loginUser", user);

        return "redirect:/reserve";
    }

    // アカウント作成画面
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

    // 登録情報変更画面
    @GetMapping("/account/edit/{id}")
    public String showAccountEditForm(
            @PathVariable Long id,
            Model model) {

        User user = userService.getUserById(id).orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        return "account-edit";
    }

    // 登録情報更新処理
    @PostMapping("/account/edit/{id}")
    public String updateAccount(
            @PathVariable Long id,
            User user,
            HttpSession session) {

        User updatedUser = userService.updateUser(id, user);

        // セッション内のユーザー情報も更新
        session.setAttribute("loginUser", updatedUser);

        return "redirect:/account/edit/" + id;
    }

    // アカウント削除確認画面
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
    public String deleteAccount(
            @PathVariable Long id,
            HttpSession session) {

        userService.deleteUser(id);

        // アカウント削除後にログイン情報も削除
        session.invalidate();

        return "redirect:/login";
    }

    // ログアウト処理
    @PostMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}