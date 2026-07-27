package com.SubmissionService.SubmissionService.outsourcedservices;

import com.SubmissionService.SubmissionService.dto.CodeExecRequest;
import com.SubmissionService.SubmissionService.dto.CodeExecutionResults;
import com.SubmissionService.SubmissionService.dto.TestCases;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "ProblemService")
public interface TestCasesClient {

    @GetMapping("/testcases/problem/{problemId}")
    List<TestCases> getTestCasesForProblem(@PathVariable Long problemId);

    @GetMapping("/testcases/problem/{problemId}")
    List<TestCases> getSampleTestCasesForProblem(@PathVariable Long problemId);

}