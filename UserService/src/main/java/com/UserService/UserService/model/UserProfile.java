package com.UserService.UserService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Table(name = "user_profiles")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    private String username;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(length = 50)
    private String country;

    @Column(length = 100)
    private String company;
}
