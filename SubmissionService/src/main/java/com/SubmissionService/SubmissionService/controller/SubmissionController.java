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

    @GetMapping("/me")
    public ResponseEntity<List<SubmissionResponse>> getMySubmissions(@RequestHeader("X-USER-ID") UUID userId) {
        return ResponseEntity.ok(submissionService.getSubmissionsByUser(userId));
    }

    @GetMapping("/problems/{problemId}")
    public ResponseEntity<List<SubmissionResponse>> getProblemSubmissions(@PathVariable Integer problemId) {
        return ResponseEntity.ok(submissionService.getSubmissionsByProblem(problemId));
    }
}
