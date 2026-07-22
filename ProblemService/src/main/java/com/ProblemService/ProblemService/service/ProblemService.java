package com.ProblemService.ProblemService.service;

import com.ProblemService.ProblemService.dtos.ProblemRequestDto;
import com.ProblemService.ProblemService.dtos.ProblemResponseDto;
import com.ProblemService.ProblemService.enums.Difficulty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProblemService {

    ProblemResponseDto createProblem(ProblemRequestDto dto);

    ProblemResponseDto getProblemById(Long id);

    ProblemResponseDto getProblemBySlug(String slug);

    Page<ProblemResponseDto> getAllProblems(Pageable pageable);

    Page<ProblemResponseDto> searchProblems(
            String keyword,
            Pageable pageable
    );

    Page<ProblemResponseDto> getProblemsByDifficulty(
            Difficulty difficulty,
            Pageable pageable
    );

    ProblemResponseDto updateProblem(
            Long id,
            ProblemRequestDto dto
    );

    void deleteProblem(Long id);
}