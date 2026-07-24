package com.AuthService.AuthService.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_verification_tokens")
@Data
public class EmailVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String token;
    @Column(nullable = false)
    private Long authUserId;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
}
