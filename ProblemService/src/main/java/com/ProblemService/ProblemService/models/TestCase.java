package com.ProblemService.ProblemService.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "test_cases")
@Data
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private  String input;
    @Lob
    private String expectedOutput;

    private Boolean sample;

    private Boolean hidden;

    @Lob
    private String explanation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="problem_id")
    private Problem problem;


}
