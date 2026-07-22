package com.SubmissionService.SubmissionService.model;

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
@Table(name = "submission_results")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionResult {

    @Id
    @Column(name = "result_id", nullable = false)
    private UUID resultId;

    @Column(name = "submission_id", nullable = false)
    private UUID submissionId;

    @Column(name = "test_case_id")
    private Integer testCaseId;

    @Column(length = 20)
    private String verdict;

    @Column(name = "actual_output", columnDefinition = "TEXT")
    private String actualOutput;

    @Column(name = "execution_time_ms")
    private Integer executionTimeMs;
}
