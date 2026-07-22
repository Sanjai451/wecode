package com.UserService.UserService.repository;

import com.UserService.UserService.model.UserStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserStatsRepository extends JpaRepository<UserStats, UUID> {
}
