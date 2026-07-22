package com.UserService.UserService.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserProfileDTO {
    private UUID userId;
    private String username;
    private String bio;
    private String avatarUrl;
    private String country;
    private String company;
}
