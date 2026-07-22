package com.SubmissionService.SubmissionService.dto;

import com.SubmissionService.SubmissionService.model.SubmissionResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionResponse {

    private UUID submissionId;
    private UUID userId;
    private Integer problemId;
    private String language;
    private String verdict;
    private Integer runtimeMs;
    private Integer memoryMb;
    private LocalDateTime submittedAt;
    private List<SubmissionResult> results;
}
