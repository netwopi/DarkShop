package com.example.buysell.controller;

import com.example.buysell.models.User;
import com.example.buysell.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
    @PostMapping("/registration")
    public String createUser(User user){
        userService.createUser(user);
        return "redirect:/login";
    }
    @GetMapping("/hello")
    public String securityUrl(){
        return "hello";
    }

}
