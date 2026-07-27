package com.SubmissionService.SubmissionService.service;

import com.SubmissionService.SubmissionService.dto.SubmissionRequest;
import com.SubmissionService.SubmissionService.dto.SubmissionResponse;
import com.SubmissionService.SubmissionService.model.Submission;

import java.util.List;
import java.util.UUID;

public interface SubmissionService {

    SubmissionResponse submit(SubmissionRequest request);

    SubmissionResponse submitForSampleTestCases(SubmissionRequest request);

    SubmissionResponse getSubmission(UUID submissionId);

    List<SubmissionResponse> getAllSubmissionsByUser(UUID userId);

    List<SubmissionResponse> getAllSubmissionsByUserForProblems(UUID userId, Long problemId);
}
