package com.UserService.UserService.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class UserStatsDTO {
    private UUID userId;
    private Integer problemsSolved;
    private Integer totalSubmissions;
}
