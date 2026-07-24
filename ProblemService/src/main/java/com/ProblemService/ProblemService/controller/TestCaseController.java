package com.ProblemService.ProblemService.controller;

import com.ProblemService.ProblemService.dtos.TestCaseDto;
import com.ProblemService.ProblemService.service.TestCaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testcases")
public class TestCaseController {
    private final TestCaseService testCaseService;

    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @PostMapping("/{problemId}")
    public ResponseEntity<TestCaseDto> createTestCase(
            @PathVariable Long problemId,
            @RequestBody TestCaseDto dto) {

        return new ResponseEntity<>(
                testCaseService.createTestCase(problemId, dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/{testCaseId}")
    public ResponseEntity<TestCaseDto> updateTestCase(
            @PathVariable Long testCaseId,
            @RequestBody TestCaseDto dto) {

        return ResponseEntity.ok(
                testCaseService.updateTestCase(testCaseId, dto));
    }

    @DeleteMapping("/{testCaseId}")
    public ResponseEntity<Void> deleteTestCase(
            @PathVariable Long testCaseId) {

        testCaseService.deleteTestCase(testCaseId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/problem/{problemId}")
    public ResponseEntity<List<TestCaseDto>> getAllTestCases(
            @PathVariable Long problemId) {

        return ResponseEntity.ok(
                testCaseService.getAllTestCases(problemId));
    }

    @GetMapping("/problem/{problemId}/sample")
    public ResponseEntity<List<TestCaseDto>> getSampleTestCases(
            @PathVariable Long problemId) {

        return ResponseEntity.ok(
                testCaseService.getSampleTestCases(problemId));
    }

    @GetMapping("/problem/{problemId}/hidden")
    public ResponseEntity<List<TestCaseDto>> getHiddenTestCases(
            @PathVariable Long problemId) {

        return ResponseEntity.ok(
                testCaseService.getHiddenTestCases(problemId));
    }
}
