package com.ProblemService.ProblemService.service;

import com.ProblemService.ProblemService.dtos.TestCaseDto;

import java.util.List;

public interface TestCaseService {

    TestCaseDto createTestCase(Long problemId, TestCaseDto dto);

    TestCaseDto updateTestCase(Long testCaseId, TestCaseDto dto);

    void deleteTestCase(Long testCaseId);

    List<TestCaseDto> getAllTestCases(Long problemId);

    List<TestCaseDto> getSampleTestCases(Long problemId);

    List<TestCaseDto> getHiddenTestCases(Long problemId);
}
