package com.SubmissionService.SubmissionService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCases {
    private Long id;

    private String input;

    private String expectedOutput;

    private Boolean sample;

    private Boolean hidden;

    private String explanation;
}