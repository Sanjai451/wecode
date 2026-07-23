package com.ExecutorService.ExecutorService.dto;

import lombok.Data;

@Data
public class CodeExecutionResults {
    private int totalCases;
    private int failedCases;
    private String message;

    public CodeExecutionResults(int totalCases, int failedCases, String message) {
        this.totalCases = totalCases;
        this.failedCases = failedCases;
        this.message = message;
    }
}