package com.ProblemService.ProblemService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestCaseDto {

    private Long id;

    private String input;

    private String expectedOutput;

    private Boolean sample;

    private Boolean hidden;

    private String explanation;
}
