package com.ProblemService.ProblemService.repository;

import com.ProblemService.ProblemService.enums.Difficulty;
import com.ProblemService.ProblemService.models.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProblemRepository
        extends JpaRepository<Problem, Long> {

    Optional<Problem> findBySlug(String slug);

    boolean existsBySlug(String slug);

    Page<Problem> findByDifficulty(
            Difficulty difficulty,
            Pageable pageable
    );

    Page<Problem> findByTitleContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );
}