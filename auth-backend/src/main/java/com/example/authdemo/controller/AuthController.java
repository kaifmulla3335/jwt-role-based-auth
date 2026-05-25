package com.example.authdemo.controller;

import com.example.authdemo.dto.AuthResponse;
import com.example.authdemo.dto.LoginRequest;
import com.example.authdemo.dto.RegisterRequest;
import com.example.authdemo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController                    // Returns JSON responses
@RequestMapping("/api/auth")       // Base URL for all methods here
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        // @Valid = run the validation annotations in RegisterRequest
        // @RequestBody = parse JSON body into RegisterRequest object
        String message = authService.register(request);
        return ResponseEntity.ok(message);
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}