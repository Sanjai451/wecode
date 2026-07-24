package com.AuthService.AuthService.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisteredRequestDto {
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String confirm_password;
}
