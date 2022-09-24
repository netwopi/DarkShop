package com.example.darkshop.controllers;

import com.example.darkshop.models.Product;
import com.example.darkshop.models.User;
import com.example.darkshop.services.ProductService;
import com.example.darkshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping("/user/editing/{user}")
    public String userEding (User user,Model model, Principal principal){
        model.addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
/*        model.addAttribute("images", user.getAvatar());*/
        model.addAttribute("user",user);
        return "profile-editing";
    }

    @PostMapping ("/create/{id}")
    public String createUser(User user,@PathVariable Long id,@RequestParam("file4") MultipartFile file4) throws IOException {
        userService.updateUser(user,id,file4);
        System.out.println("2" + user);
        System.out.println("3" + file4);
        return "redirect:/login";
    }
}
