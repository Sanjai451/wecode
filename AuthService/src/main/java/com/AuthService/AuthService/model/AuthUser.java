package com.AuthService.AuthService.model;

import com.AuthService.AuthService.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(schema = "auth_users")
@Data
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private Role role;
    private Boolean enabled=false;
    private Boolean accountLocked=true;
    private Boolean emailVerified=false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



}
