package com.UserService.UserService.service.impl;

import com.UserService.UserService.dto.UpdateProfileRequest;
import com.UserService.UserService.dto.UserProfileDTO;
import com.UserService.UserService.dto.UserStatsDTO;
import com.UserService.UserService.model.UserProfile;
import com.UserService.UserService.model.UserStats;
import com.UserService.UserService.repository.UserProfileRepository;
import com.UserService.UserService.repository.UserStatsRepository;
import com.UserService.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserProfileRepository profileRepository;

    @Autowired
    private UserStatsRepository statsRepository;

    @Override
    public UserProfile getCurrentUser(UUID userId) {

        return profileRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found"));
    }

    @Override
    public UserProfile getUserById(UUID id) {

        return profileRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    @Override
    public UserProfile updateProfile(UUID userId,
                                     UpdateProfileRequest request) {

        UserProfile profile = profileRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found"));

        if(request.getBio() != null) {
            profile.setBio(request.getBio());
        }
        if(request.getAvatarUrl() != null) {
            profile.setAvatarUrl(request.getAvatarUrl());
        }
        if(request.getCountry() != null) {
            profile.setCountry(request.getCountry());
        }
        if(request.getCompany() != null) {
            profile.setCompany(request.getCompany());
        }

        return profileRepository.save(profile);
    }

    @Override
    public UserStats getUserStats(UUID id) {

        return statsRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Stats not found"));
    }

    @Override
    public UserProfile createProfile(UserProfileDTO userProfile) {

        if(userProfile.getUserId() == null || userProfile.getUsername() == null) {
            throw new RuntimeException("User ID & Username is required");
        }

        if (profileRepository.existsById(userProfile.getUserId())) {
            throw new RuntimeException("Profile already exists");
        }
        if(checkUsernameTakenOrNot(userProfile.getUsername())){
            throw new RuntimeException("Username already taken try with some other username");
        }

        UserProfile profile = new UserProfile();

        profile.setUserId(userProfile.getUserId());

        if(userProfile.getUsername() != null) {
                profile.setUsername(userProfile.getUsername());
        }
        if(userProfile.getBio() != null){
                profile.setBio(userProfile.getBio());
        }
        if(userProfile.getAvatarUrl() != null){
                profile.setAvatarUrl(userProfile.getAvatarUrl());
        }
        if(userProfile.getCountry() != null){
                profile.setCountry(userProfile.getCountry());
        }
        if(userProfile.getCompany() != null){
                profile.setCompany(userProfile.getCompany());;
        }

        profileRepository.save(profile);

        UserStats stats = UserStats.builder()
                .userId(userProfile.getUserId())
                .problemsSolved(0)
                .totalSubmissions(0)
                .build();

        statsRepository.save(stats);

        return profile;
    }

    @Override
    public void deleteProfile(UUID id) {

        statsRepository.deleteById(id);
        profileRepository.deleteById(id);
    }

    @Override
    public UserStats updateStats(UUID id,
                                 boolean accepted) {

        UserStats stats = statsRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Stats not found"));

        stats.setTotalSubmissions(
                stats.getTotalSubmissions() + 1);

        return statsRepository.save(stats);
    }

    @Override
    public UserProfile getUserByUsername(String username) {
        return profileRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username not found"));
    }

    public boolean checkUsernameTakenOrNot(String username){
        return profileRepository.findByUsername(username).isPresent();
    }

    @Override
    public List<UserProfile> getAllUser() {
        return profileRepository.findAll();
    }
}
