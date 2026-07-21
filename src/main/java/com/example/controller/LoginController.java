package com.example.controller;

import com.example.form.User_regiser;
import com.example.model.User;
import com.example.model.Userprofile;
import com.example.service.UserService;
import com.example.service.UserprofileService;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/auth")
public class LoginController {

    private final UserService userService;
    private final UserprofileService userprofileService;

    public LoginController(UserService userService,UserprofileService userprofileService) {
        this.userService = userService;
        this.userprofileService = userprofileService;
    }

    // GET /auth/login
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // ログイン成功時に予約画面に一覧へ
/*
@PostMapping("/login")
public String login(
    @RequestParam String username,
    @RequestParam String password,
    HttpSession session,
    Model model) {
        
    User user = userService.login(username, password)
    .orElse(null);
    
    if (user == null) {
        model.addAttribute(
            "error",
            "ユーザー名またはパスワードが違います");
            
            return "login";
        }
        
        session.setAttribute("loginUser", user);
        
        return "redirect:/user/reserve";
    }
    */
    
   
   // GET /auth/register
    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        model.addAttribute("user", new User_regiser());

        return "register";
    }

    // POST /auth/register
    @PostMapping("/register")
    public String registerUser(User_regiser user_regiser) {
        
    User user = new User();
    Userprofile user_profile = new Userprofile();

    user.setUsername(user_regiser.getUsername());
    user.setPassword(user_regiser.getPassword());

    userService.createUser(user);

    user_profile.setUser(user);
    user_profile.setEmail(user_regiser.getEmail());
    user_profile.setPhonenumber(user_regiser.getPhonenumber());

    userprofileService.createUserprofile(user_profile);

    return "redirect:/auth/login";
    }


// GET /auth/account/edit/{id}
@GetMapping("/account/edit/{id}")
public String showAccountEditForm(
    @PathVariable Long id,
    Model model) {
        
    User user = userService.getUserById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "指定されたユーザーが存在しません"));

        model.addAttribute("user", user);

        return "account-edit";
    }

    // POST /auth/account/edit/{id}
    @PostMapping("/account/edit/{id}")
    public String updateAccount(
            @PathVariable Long id,
            User user,
            HttpSession session) {

        userService.getUserById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "指定されたユーザーが存在しません"));

        User updatedUser = userService.updateUser(id, user);

        session.setAttribute("loginUser", updatedUser);

        return "redirect:/auth/account/edit/" + id;
    }

    // GET /auth/account/delete/{id}
    @GetMapping("/account/delete/{id}")
    public String showDeleteForm(
            @PathVariable Long id,
            Model model) {

        User user = userService.getUserById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "指定されたユーザーが存在しません"));

        model.addAttribute("user", user);

        return "account-delete";
    }

    // POST /auth/account/delete/{id}
    @PostMapping("/account/delete/{id}")
    public String deleteAccount(
            @PathVariable Long id,
            HttpSession session) {

        userService.getUserById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "指定されたユーザーが存在しません"));

        userService.deleteUser(id);

        session.invalidate();

        return "redirect:/auth/login";
    }

    // POST /auth/logout
    @PostMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/auth/login";
    }
}