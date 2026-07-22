package com.UserService.UserService.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateProfileRequest {
    private UUID id;
    private String bio;
    private String avatarUrl;
    private String country;
    private String company;

}