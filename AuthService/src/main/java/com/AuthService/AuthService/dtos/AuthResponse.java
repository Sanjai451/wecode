package com.AuthService.AuthService.dtos;

import com.AuthService.AuthService.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private Long userId;
    private String email;
    private Role role;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
}
