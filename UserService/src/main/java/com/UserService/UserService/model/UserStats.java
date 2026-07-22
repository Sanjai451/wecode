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
@Table(name = "user_stats")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStats {
    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "problems_solved")
    private Integer problemsSolved = 0;

    @Column(name = "total_submissions")
    private Integer totalSubmissions = 0;
}
