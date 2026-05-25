package com.example.authdemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    // GET /api/dashboard/user
    // Only accessible by users with ROLE_USER (enforced by SecurityConfig)
    @GetMapping("/user")
    public ResponseEntity<String> userDashboard() {
        return ResponseEntity.ok("Welcome to User Dashboard! Your JWT token is valid.");
    }

    // GET /api/dashboard/admin
    // Only accessible by users with ROLE_ADMIN
    @GetMapping("/admin")
    public ResponseEntity<String> adminDashboard() {
        return ResponseEntity.ok("Welcome Admin! You have full access.");
    }
}