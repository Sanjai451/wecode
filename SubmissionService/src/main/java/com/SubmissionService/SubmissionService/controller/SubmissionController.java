package com.SubmissionService.SubmissionService.controller;

import com.SubmissionService.SubmissionService.dto.SubmissionRequest;
import com.SubmissionService.SubmissionService.dto.SubmissionResponse;
import com.SubmissionService.SubmissionService.service.SubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping("/submit")
    public ResponseEntity<SubmissionResponse> submit(@RequestBody SubmissionRequest request) {
        SubmissionResponse response = submissionService.submit(request);
        return ResponseEntity.accepted().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubmissionResponse> getSubmission(@PathVariable UUID id) {
        return ResponseEntity.ok(submissionService.getSubmission(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SubmissionResponse>> getUserSubmissions(
            @PathVariable UUID userId) {

        return ResponseEntity.ok(
                submissionService.getAllSubmissionsByUser(userId)
        );
    }

    @GetMapping("/user/{userId}/problem/{problemId}")
    public ResponseEntity<List<SubmissionResponse>> getUserSubmissionsForProblem(
            @PathVariable UUID userId,
            @PathVariable Long problemId) {

        return ResponseEntity.ok(
                submissionService.getAllSubmissionsByUserForProblems(userId, problemId)
        );
    }
}
