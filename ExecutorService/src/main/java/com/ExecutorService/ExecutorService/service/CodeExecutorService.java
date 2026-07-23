package com.ExecutorService.ExecutorService.service;

import com.ExecutorService.ExecutorService.dto.CodeExecutionResults;
import com.ExecutorService.ExecutorService.executors.CodeExecutor;
import com.ExecutorService.ExecutorService.util.CreateExecutorUtil;
import com.ExecutorService.ExecutorService.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class CodeExecutorService {

    @Autowired
    private FileUtil fileUtility;

    public CodeExecutionResults executeCode(String code, String lang, List<String> inputs, List<String> expectedOutputs){
        Path executionDir = null;

        try {

            executionDir = fileUtility.createExecutionDirectory();

            CodeExecutor ce = CreateExecutorUtil.create(lang);

            if(ce == null){
                return new CodeExecutionResults(-1, -1, "Failed to Load Language " + lang);
            }

            if(! ce.compile(executionDir, code)){
                return new CodeExecutionResults(-1, -1, "Code Compilation Failed");
            }

            int passedCount = 0, failedCount = 0;
            for (int i = 0; i < inputs.size(); i++) {
                String codeResponse = ce.execute(executionDir, inputs.get(i));
                System.out.println("code resp : " + codeResponse + " | exp : " + expectedOutputs.get(i));
                if (!codeResponse.equals(expectedOutputs.get(i))) {
                    return new CodeExecutionResults(inputs.size(), inputs.size() - passedCount, "Test Cases Failed");
                }else{
                    passedCount++;
                }
            }

            return new CodeExecutionResults(inputs.size(), 0, "Code Passed all Test Cases");

        } catch (Exception e) {
            return new CodeExecutionResults(-1, -1, e.getMessage());
        }
        finally {
            if(executionDir != null){
                fileUtility.removeCodeFiles(executionDir);
            }
        }
    }
}
