package com.SubmissionService.SubmissionService.service.impl;

import com.SubmissionService.SubmissionService.dto.SubmissionRequest;
import com.SubmissionService.SubmissionService.dto.SubmissionResponse;
import com.SubmissionService.SubmissionService.model.Submission;
import com.SubmissionService.SubmissionService.model.SubmissionResult;
import com.SubmissionService.SubmissionService.repository.SubmissionRepository;
import com.SubmissionService.SubmissionService.repository.SubmissionResultRepository;
import com.SubmissionService.SubmissionService.service.SubmissionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final SubmissionResultRepository submissionResultRepository;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository,
                                 SubmissionResultRepository submissionResultRepository) {
        this.submissionRepository = submissionRepository;
        this.submissionResultRepository = submissionResultRepository;
    }

    @Override
    public SubmissionResponse submit(SubmissionRequest request) {
        Submission submission = Submission.builder()
                .submissionId(UUID.randomUUID())
                .userId(request.getUserId())
                .problemId(request.getProblemId())
                .language(request.getLanguage())
                .code(request.getCode())
                .verdict("PENDING")
                .submittedAt(LocalDateTime.now())
                .build();

        submission = submissionRepository.save(submission);

        // logic for code execution

        return mapToResponse(submission, List.of());
    }

    @Override
    public SubmissionResponse getSubmission(UUID submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found: " + submissionId));

        List<SubmissionResult> results = submissionResultRepository.findBySubmissionId(submissionId);

        return mapToResponse(submission, results);
    }

    @Override
    public List<SubmissionResponse> getSubmissionsByUser(UUID userId) {
        List<Submission> submissions = submissionRepository.findByUserIdOrderBySubmittedAtDesc(userId);
        return submissions.stream()
                .map(submission -> mapToResponse(submission, submissionResultRepository.findBySubmissionId(submission.getSubmissionId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubmissionResponse> getSubmissionsByProblem(Integer problemId) {
        List<Submission> submissions = submissionRepository.findByProblemIdOrderBySubmittedAtDesc(problemId);
        return submissions.stream()
                .map(submission -> mapToResponse(submission, submissionResultRepository.findBySubmissionId(submission.getSubmissionId())))
                .collect(Collectors.toList());
    }

    private SubmissionResponse mapToResponse(Submission submission, List<SubmissionResult> results) {
        return SubmissionResponse.builder()
                .submissionId(submission.getSubmissionId())
                .userId(submission.getUserId())
                .problemId(submission.getProblemId())
                .language(submission.getLanguage())
                .verdict(submission.getVerdict())
                .runtimeMs(submission.getRuntimeMs())
                .memoryMb(submission.getMemoryMb())
                .submittedAt(submission.getSubmittedAt())
                .results(results)
                .build();
    }
}
