package com.SubmissionService.SubmissionService.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SubmissionRequest {

    private UUID userId;
    private Integer problemId;
    private String language;
    private String code;
}
