package ru.georgy.NauJava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/register")
    public String registerView() {
        return "register";
    }

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }
}
