package com.example.darkshop.controllers;

import com.example.darkshop.models.User;
import com.example.darkshop.services.ProductService;
import com.example.darkshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/login")
    public String login(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Principal principal,Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("products", user.getProducts());;
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        return "profile";
    }

    @GetMapping("/registration")
    public String registration(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "registration";
    }


    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Пользователь с email: " + user.getEmail() + " уже существует");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model, Principal principal) {
        model.addAttribute("user", user);
        model.addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
        model.addAttribute("products", user.getProducts());
        return "user-info" ;


    }
    @GetMapping("/editing/{user}")
    public String userEditing(User user, Model model, Principal principal){
        model.addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
        model.addAttribute("user",user);
        return "profile-editing";
    }

    @PostMapping ("/create/{id}")
    public String createUser(User user, @PathVariable Integer id) throws IOException {
        userService.updateUser(user,id);
        return "redirect:/login";
    }
}
