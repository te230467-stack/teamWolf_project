package com.example.controller;

import com.example.model.Reserve;
import com.example.service.ReserveService;
import com.example.service.UserService;
import com.example.service.UserprofileService;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ReserveService reserveService;
    private final UserService userService;
    private final UserprofileService userprofileService;
    public AdminController(ReserveService reserveService, UserService userService, UserprofileService userprofileService) {
        this.reserveService = reserveService;
        this.userService = userService;
        this.userprofileService = userprofileService;
    }

    @GetMapping("/dashboard")
    public String showdashboard(Model model) {
        return "dashboard_admin";
    }

    // GET /admin/reserves
    @GetMapping("/reserves")
    public String showReserveList(Model model,Authentication authentication) {

        model.addAttribute(
                "reserves",
                reserveService.getAllReserves());
        return "admin_reservelist";
    }

    // GET /admin/reserves/new
    @GetMapping("/reserves/new")
    public String showReserveForm(Model model) {

        model.addAttribute("reserve", new Reserve());

        return "admin-reserve-form";
    }

    // POST /admin/reserves
    @PostMapping("/reserves")
    public String createReserve(Reserve reserve) {

        reserveService.createReserve(reserve);

        return "redirect:/admin/reserves";
    }

    // GET /admin/reserves/edit/{id}
    @GetMapping("/reserves/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {

        Reserve reserve = reserveService.getReserveById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "指定された予約が存在しません"));

        model.addAttribute("reserve", reserve);

        return "admin-reserve-edit";
    }

    // POST /admin/reserves/edit/{id}
    @PostMapping("/reserves/edit/{id}")
    public String updateReserve(
            @PathVariable Long id,
            Reserve reserve) {

        reserveService.getReserveById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "指定された予約が存在しません"));

        reserveService.updateReserve(id, reserve);

        return "redirect:/admin/reserves";
    }

    // POST /admin/reserves/delete/{id}
    @PostMapping("/reserves/delete/{id}")
    public String deleteReserve(@PathVariable Long id) {

        reserveService.getReserveById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "指定された予約が存在しません"));

        reserveService.deleteReserve(id);

        return "redirect:/admin/reserves";
    }
}