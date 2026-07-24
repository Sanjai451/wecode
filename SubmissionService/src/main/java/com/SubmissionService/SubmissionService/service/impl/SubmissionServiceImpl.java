package com.SubmissionService.SubmissionService.service.impl;

import com.SubmissionService.SubmissionService.dto.*;
import com.SubmissionService.SubmissionService.model.Submission;
import com.SubmissionService.SubmissionService.outsourcedservices.CodeExecutorClient;
import com.SubmissionService.SubmissionService.repository.SubmissionRepository;
import com.SubmissionService.SubmissionService.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    @Autowired
    private CodeExecutorClient codeExecutorClient;

    @Override
    public SubmissionResponse submit(SubmissionRequest request) {

        // Fetch list of inputs and Expected outputs from Problem Service
        List<InputsAndExpectedOutputs> iae = getInputAndExpData(request.getProblemId());
        if(iae == null){
            return SubmissionResponse.builder()
                    .message("NO TEST CASES AVAILABLE")
                    .build();
        }

        Submission submission = Submission.builder()
                .submissionId(UUID.randomUUID())
                .userId(request.getUserId())
                .problemId(request.getProblemId())
                .language(request.getLanguage())
                .code(request.getCode())
                .submittedAt(LocalDateTime.now())
                .build();

        CodeExecRequest req = new CodeExecRequest();
        req.setCode(request.getCode());
        req.setLang(request.getLanguage());

//        ArrayList<String > inputs = new ArrayList<>();
//        inputs.add("5 1");
//        ArrayList<String > expectedOutputs = new ArrayList<>();
//        expectedOutputs.add("6");
//        req.setInputs(inputs);
//        req.setExpectedOutputs(expectedOutputs);

        req.setInputs(getInputs(iae));
        req.setExpectedOutputs(getExpOutputs(iae));

        CodeExecutionResults codeExecResp = codeExecutorClient.executeCode(req);

        if(codeExecResp.getMessage().toLowerCase().contains("pass")){
            submission.setVerdict("PASSED");
        }
        else{
            submission.setVerdict("FAILED");
        }

        submission.setFailedCases(codeExecResp.getFailedCases());
        submission.setTotalCases(codeExecResp.getTotalCases());
        submission.setMessage(codeExecResp.getMessage());

        submission = submissionRepository.save(submission);

        return mapToResponse(submission);
    }

    @Override
    public SubmissionResponse getSubmission(UUID submissionId) {
        Submission submission =  submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found: " + submissionId));
        return mapToResponse(submission);
    }

    @Override
    public List<SubmissionResponse> getAllSubmissionsByUser(UUID userId) {
        return submissionRepository
                .findByUserIdOrderBySubmittedAtDesc(userId)
                .stream()
                .map(this::mapToResponse).toList();
    }

    @Override
    public List<SubmissionResponse> getAllSubmissionsByUserForProblems(UUID userId, UUID problemId) {
        return submissionRepository
                .findByUserIdAndProblemIdOrderBySubmittedAtDesc(userId, problemId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private SubmissionResponse mapToResponse(Submission submission) {
        return SubmissionResponse.builder()
                .submissionId(submission.getSubmissionId())
                .userId(submission.getUserId())
                .problemId(submission.getProblemId())
                .language(submission.getLanguage())
                .verdict(submission.getVerdict())
                .runtimeMs(submission.getRuntimeMs())
                .memoryMb(submission.getMemoryMb())
                .submittedAt(submission.getSubmittedAt())
                .failedCases(submission.getFailedCases())
                .totalCases(submission.getTotalCases())
                .message(submission.getMessage())
                .build();
    }

    private List<InputsAndExpectedOutputs> getInputAndExpData(UUID problemId){
        // get from Problem service
        return null;
    }

    private ArrayList<String> getInputs(List<InputsAndExpectedOutputs> iae){
        return new ArrayList<>(iae.stream().map(InputsAndExpectedOutputs::getInput).toList());
    }

    private ArrayList<String> getExpOutputs(List<InputsAndExpectedOutputs> iae){
        return new ArrayList<>(iae.stream().map(InputsAndExpectedOutputs::getExpectedOutputs).toList());
    }
}
