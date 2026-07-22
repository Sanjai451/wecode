package com.SubmissionService.SubmissionService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "submissions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Submission {

    @Id
    @Column(name = "submission_id", nullable = false)
    private UUID submissionId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "problem_id", nullable = false)
    private Integer problemId;

    @Column(length = 20)
    private String language;

    @Column(columnDefinition = "TEXT")
    private String code;

    @Column(length = 20)
    private String verdict;

    @Column(name = "runtime_ms")
    private Integer runtimeMs;

    @Column(name = "memory_mb")
    private Integer memoryMb;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
}
