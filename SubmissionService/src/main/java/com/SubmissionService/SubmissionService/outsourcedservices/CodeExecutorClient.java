package com.SubmissionService.SubmissionService.outsourcedservices;

import com.SubmissionService.SubmissionService.dto.CodeExecRequest;
import com.SubmissionService.SubmissionService.dto.CodeExecutionResults;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ExecutorService")
public interface CodeExecutorClient {

    @PostMapping("/execute/code")
    CodeExecutionResults executeCode(@RequestBody CodeExecRequest request);

}
