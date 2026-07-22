package com.ProblemService.ProblemService.dtos;

import com.ProblemService.ProblemService.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProblemResponseDto {

    private Long id;

    private String title;

    private String slug;

    private String description;

    private String inputFormat;

    private String outputFormat;

    private String constraints;

    private String explanation;

    private Difficulty difficulty;

    private Integer timeLimit;

    private Integer memoryLimit;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
