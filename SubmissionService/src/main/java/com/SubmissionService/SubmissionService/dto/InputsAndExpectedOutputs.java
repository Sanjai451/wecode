package com.SubmissionService.SubmissionService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputsAndExpectedOutputs {
    private String input;
    private String expectedOutputs;
}
