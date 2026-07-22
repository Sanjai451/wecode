package com.UserService.UserService.service;

import com.UserService.UserService.dto.UpdateProfileRequest;
import com.UserService.UserService.dto.UserProfileDTO;
import com.UserService.UserService.dto.UserStatsDTO;
import com.UserService.UserService.model.UserProfile;
import com.UserService.UserService.model.UserStats;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserProfile getCurrentUser(UUID userId);

    UserProfile getUserById(UUID id);

    UserProfile updateProfile(UUID userId,
                              UpdateProfileRequest request);

    UserStats getUserStats(UUID id);

    UserProfile createProfile(UserProfileDTO userProfileDTO);

    void deleteProfile(UUID id);

    UserStats updateStats(UUID id,
                          boolean accepted);

    UserProfile getUserByUsername(String username);

    List<UserProfile> getAllUser();

}
