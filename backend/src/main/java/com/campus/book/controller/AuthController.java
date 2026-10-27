package com.campus.book.controller;

import com.campus.book.dto.AuthResponse;
import com.campus.book.dto.LoginRequest;
import com.campus.book.dto.RegisterRequest;
import com.campus.book.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest req) {
        userService.register(req);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        return userService.login(req);
    }
}
