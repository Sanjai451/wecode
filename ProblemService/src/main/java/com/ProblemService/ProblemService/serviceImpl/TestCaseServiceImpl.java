package com.ProblemService.ProblemService.serviceImpl;

import com.ProblemService.ProblemService.dtos.TestCaseDto;
import com.ProblemService.ProblemService.exception.ResourceNotFoundException;
import com.ProblemService.ProblemService.models.Problem;
import com.ProblemService.ProblemService.models.TestCase;
import com.ProblemService.ProblemService.repository.ProblemRepository;
import com.ProblemService.ProblemService.repository.TestCaseRepository;
import com.ProblemService.ProblemService.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestCaseServiceImpl implements TestCaseService {
    private final TestCaseRepository testCaseRepository;
    private final ProblemRepository problemRepository;
    private final ModelMapper modelMapper;

    @Override
    public TestCaseDto createTestCase(Long problemId, TestCaseDto dto) {

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Problem not found"));

        TestCase testCase = modelMapper.map(dto, TestCase.class);

        testCase.setProblem(problem);

        TestCase saved = testCaseRepository.save(testCase);

        return modelMapper.map(saved, TestCaseDto.class);
    }

    @Override
    public TestCaseDto updateTestCase(Long testCaseId, TestCaseDto dto) {

        TestCase testCase = testCaseRepository.findById(testCaseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Test case not found"));

        testCase.setInput(dto.getInput());
        testCase.setExpectedOutput(dto.getExpectedOutput());
        testCase.setSample(dto.getSample());
        testCase.setHidden(dto.getHidden());
        testCase.setExplanation(dto.getExplanation());

        TestCase updated = testCaseRepository.save(testCase);

        return modelMapper.map(updated, TestCaseDto.class);
    }

    @Override
    public void deleteTestCase(Long testCaseId) {

        TestCase testCase = testCaseRepository.findById(testCaseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Test case not found"));

        testCaseRepository.delete(testCase);
    }

    @Override
    public List<TestCaseDto> getAllTestCases(Long problemId) {

        return testCaseRepository.findByProblemId(problemId)
                .stream()
                .map(testCase ->
                        modelMapper.map(testCase, TestCaseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TestCaseDto> getSampleTestCases(Long problemId) {

        return testCaseRepository
                .findByProblemIdAndHiddenFalse(problemId)
                .stream()
                .map(testCase ->
                        modelMapper.map(testCase, TestCaseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TestCaseDto> getHiddenTestCases(Long problemId) {

        return testCaseRepository
                .findByProblemIdAndHiddenTrue(problemId)
                .stream()
                .map(testCase ->
                        modelMapper.map(testCase, TestCaseDto.class))
                .collect(Collectors.toList());
    }
}
