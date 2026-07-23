package com.ExecutorService.ExecutorService.dto;

import lombok.Data;

import java.util.List;

@Data
public class CodeExecRequest {
    private String lang;
    private String code;
    private List<String> inputs;
    private List<String> expectedOutputs;
}
