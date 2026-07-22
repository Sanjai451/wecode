package com.UserService.UserService.repository;

import com.UserService.UserService.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    public Optional<UserProfile> findByUsername(String username);
}
