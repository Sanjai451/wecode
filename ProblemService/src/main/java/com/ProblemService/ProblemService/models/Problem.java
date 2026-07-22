package com.ProblemService.ProblemService.models;

import com.ProblemService.ProblemService.enums.Difficulty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "problems")
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(unique = true)
    private String slug;

    @Lob
    private String description;

    @Lob
    private String inputFormat;

    @Lob
    private String outputFormat;

    @Lob
    private String constraints;

    @Lob
    private String explanation;

    private Difficulty difficulty;

    private Integer timeLimit;      // milliseconds

    private Integer memoryLimit;    // MB

    private Boolean active;

    private Long createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}