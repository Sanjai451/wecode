package com.AuthService.AuthService.controller;

import com.AuthService.AuthService.dtos.AuthResponse;
import com.AuthService.AuthService.dtos.MessageResponse;
import com.AuthService.AuthService.dtos.RegisteredRequestDto;
import com.AuthService.AuthService.dtos.RequestLoginDto;
import com.AuthService.AuthService.service.AuthUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthUserService authService;

    public AuthController(AuthUserService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(
            @Valid @RequestBody RegisteredRequestDto request) {

        MessageResponse response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody RequestLoginDto request) {

        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<MessageResponse> verifyEmail(
            @RequestParam String token) {

        MessageResponse response = authService.verifyEmail(token);

        return ResponseEntity.ok(response);
    }
}
