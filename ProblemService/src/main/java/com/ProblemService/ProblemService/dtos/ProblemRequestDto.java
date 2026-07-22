package com.ProblemService.ProblemService.dtos;

import com.ProblemService.ProblemService.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemRequestDto {

    private String title;

    private String description;

    private String inputFormat;

    private String outputFormat;

    private String constraints;

    private String explanation;

    private Difficulty difficulty;

    private Integer timeLimit;

    private Integer memoryLimit;

    private Boolean active;
}
