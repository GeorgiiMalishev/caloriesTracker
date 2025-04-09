package ru.georgy.NauJava.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.georgy.NauJava.service.user.UserInput;
import ru.georgy.NauJava.service.user.UserResponse;
import ru.georgy.NauJava.service.user.UserService;

@RestController
public class AuthRestController {
    private final UserService userService;

    @Autowired
    public AuthRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("api/register")
    public UserResponse registerUser(@Valid @RequestBody UserInput userInput){
        return userService.registerUser(userInput);
    }
}
