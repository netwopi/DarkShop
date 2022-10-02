package com.example.darkshop.controller;

import com.example.darkshop.model.User;
import com.example.darkshop.model.enums.Role;
import com.example.darkshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("users", userService.list());
        return "admin";
    }

    @GetMapping("/admin/user/ban/{id}")
    public String userBan(@PathVariable("id") Integer id) {
        userService.banUser(id);
        return "redirect:/admin";
    }

    @PutMapping("/admin/user/edit/{user}")
    public String userEdit(@PathVariable("user") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    @PutMapping("/admin/user/edit")
    public String userEdit(@RequestParam("userId") User user, @RequestParam Map<String, String> form) {
        userService.changeUserRoles(user, form);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/user/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userService.userDelete(id);
        return "redirect:/admin";
    }

}
