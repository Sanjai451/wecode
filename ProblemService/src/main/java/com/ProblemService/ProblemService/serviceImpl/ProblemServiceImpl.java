package com.ProblemService.ProblemService.serviceImpl;

import com.ProblemService.ProblemService.dtos.ProblemRequestDto;
import com.ProblemService.ProblemService.dtos.ProblemResponseDto;
import com.ProblemService.ProblemService.enums.Difficulty;
import com.ProblemService.ProblemService.exception.ResourceNotFoundException;
import com.ProblemService.ProblemService.models.Problem;
import com.ProblemService.ProblemService.repository.ProblemRepository;
import com.ProblemService.ProblemService.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;

    @Override
    public ProblemResponseDto createProblem(ProblemRequestDto dto) {

        String slug = generateUniqueSlug(dto.getTitle());

        Problem problem = new Problem();

        problem.setTitle(dto.getTitle());
        problem.setSlug(slug);
        problem.setDescription(dto.getDescription());
        problem.setInputFormat(dto.getInputFormat());
        problem.setOutputFormat(dto.getOutputFormat());
        problem.setConstraints(dto.getConstraints());
        problem.setExplanation(dto.getExplanation());
        problem.setDifficulty(dto.getDifficulty());
        problem.setTimeLimit(dto.getTimeLimit());
        problem.setMemoryLimit(dto.getMemoryLimit());
        problem.setActive(
                dto.getActive() != null ? dto.getActive() : true
        );
        problem.setCreatedAt(LocalDateTime.now());
        problem.setUpdatedAt(LocalDateTime.now());

        Problem savedProblem = problemRepository.save(problem);

        return mapToResponse(savedProblem);
    }

    @Override
    @Transactional(readOnly = true)
    public ProblemResponseDto getProblemById(Long id) {

        Problem problem = problemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Problem not found with id: " + id
                        )
                );

        return mapToResponse(problem);
    }

    @Override
    @Transactional(readOnly = true)
    public ProblemResponseDto getProblemBySlug(String slug) {

        Problem problem = problemRepository.findBySlug(slug)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Problem not found with slug: " + slug
                        )
                );

        return mapToResponse(problem);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProblemResponseDto> getAllProblems(Pageable pageable) {

        return problemRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProblemResponseDto> searchProblems(
            String keyword,
            Pageable pageable
    ) {

        return problemRepository
                .findByTitleContainingIgnoreCase(keyword, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional
    public Page<ProblemResponseDto> getProblemsByDifficulty(Difficulty difficulty, Pageable pageable) {
        return problemRepository
                .findByDifficulty(difficulty, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public ProblemResponseDto updateProblem(
            Long id,
            ProblemRequestDto dto
    ) {

        Problem problem = problemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Problem not found with id: " + id
                        )
                );

        if (!problem.getTitle().equals(dto.getTitle())) {
            problem.setSlug(generateUniqueSlug(dto.getTitle()));
        }

        problem.setTitle(dto.getTitle());
        problem.setDescription(dto.getDescription());
        problem.setInputFormat(dto.getInputFormat());
        problem.setOutputFormat(dto.getOutputFormat());
        problem.setConstraints(dto.getConstraints());
        problem.setExplanation(dto.getExplanation());
        problem.setDifficulty(dto.getDifficulty());
        problem.setTimeLimit(dto.getTimeLimit());
        problem.setMemoryLimit(dto.getMemoryLimit());
        problem.setActive(dto.getActive());
        problem.setUpdatedAt(LocalDateTime.now());

        Problem updatedProblem = problemRepository.save(problem);

        return mapToResponse(updatedProblem);
    }

    @Override
    public void deleteProblem(Long id) {

        Problem problem = problemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Problem not found with id: " + id
                        )
                );

        problemRepository.delete(problem);
    }

    private String generateUniqueSlug(String title) {

        String baseSlug = title
                .toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");

        String slug = baseSlug;
        int count = 1;

        while (problemRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + count;
            count++;
        }

        return slug;
    }

    private ProblemResponseDto mapToResponse(Problem problem) {

        ProblemResponseDto dto = new ProblemResponseDto();

        dto.setId(problem.getId());
        dto.setTitle(problem.getTitle());
        dto.setSlug(problem.getSlug());
        dto.setDescription(problem.getDescription());
        dto.setInputFormat(problem.getInputFormat());
        dto.setOutputFormat(problem.getOutputFormat());
        dto.setConstraints(problem.getConstraints());
        dto.setExplanation(problem.getExplanation());
        dto.setDifficulty(problem.getDifficulty());
        dto.setTimeLimit(problem.getTimeLimit());
        dto.setMemoryLimit(problem.getMemoryLimit());
        dto.setActive(problem.getActive());
        dto.setCreatedAt(problem.getCreatedAt());
        dto.setUpdatedAt(problem.getUpdatedAt());

        return dto;
    }
}