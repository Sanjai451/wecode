package com.ProblemService.ProblemService.controller;


import com.ProblemService.ProblemService.dtos.ProblemRequestDto;
import com.ProblemService.ProblemService.dtos.ProblemResponseDto;
import com.ProblemService.ProblemService.enums.Difficulty;
import com.ProblemService.ProblemService.service.ProblemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    // Create a new problem
    @PostMapping("/create")
    public ResponseEntity<ProblemResponseDto> createProblem(
            @Valid @RequestBody ProblemRequestDto dto) {

        ProblemResponseDto createdProblem =
                problemService.createProblem(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdProblem);
    }

    // Get problem by database ID
    @GetMapping("/{id}")
    public ResponseEntity<ProblemResponseDto> getProblemById(
            @PathVariable Long id) {

        ProblemResponseDto problem =
                problemService.getProblemById(id);

        return ResponseEntity.ok(problem);
    }

    // Get problem by slug
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ProblemResponseDto> getProblemBySlug(
            @PathVariable String slug) {

        ProblemResponseDto problem =
                problemService.getProblemBySlug(slug);

        return ResponseEntity.ok(problem);
    }

    // Get all problems with pagination
    @GetMapping
    public ResponseEntity<Page<ProblemResponseDto>> getAllProblems(
            Pageable pageable) {

        Page<ProblemResponseDto> problems =
                problemService.getAllProblems(pageable);

        return ResponseEntity.ok(problems);
    }

    // Search problems by title
    @GetMapping("/search")
    public ResponseEntity<Page<ProblemResponseDto>> searchProblems(
            @RequestParam String keyword,
            Pageable pageable) {

        Page<ProblemResponseDto> problems =
                problemService.searchProblems(keyword, pageable);

        return ResponseEntity.ok(problems);
    }

    // Filter problems by difficulty
    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<Page<ProblemResponseDto>> getByDifficulty(
            @PathVariable Difficulty difficulty,
            Pageable pageable) {

        Page<ProblemResponseDto> problems =
                problemService.getProblemsByDifficulty(
                        difficulty,
                        pageable
                );

        return ResponseEntity.ok(problems);
    }

    // Update an existing problem
    @PutMapping("/{id}")
    public ResponseEntity<ProblemResponseDto> updateProblem(
            @PathVariable Long id,
            @Valid @RequestBody ProblemRequestDto dto) {

        ProblemResponseDto updatedProblem =
                problemService.updateProblem(id, dto);

        return ResponseEntity.ok(updatedProblem);
    }

    // Delete a problem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProblem(
            @PathVariable Long id) {

        problemService.deleteProblem(id);

        return ResponseEntity.noContent().build();
    }
}