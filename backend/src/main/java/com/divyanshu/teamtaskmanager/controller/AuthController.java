package com.divyanshu.teamtaskmanager.controller;

import com.divyanshu.teamtaskmanager.dto.auth.AuthResponse;
import com.divyanshu.teamtaskmanager.dto.auth.LoginRequest;
import com.divyanshu.teamtaskmanager.dto.auth.RegisterRequest;
import com.divyanshu.teamtaskmanager.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        System.out.println("REGISTER API HIT");

        return ResponseEntity.ok(
                authService.register(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        System.out.println("LOGIN API HIT");

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}