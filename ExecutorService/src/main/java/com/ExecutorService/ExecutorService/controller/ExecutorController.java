package com.ExecutorService.ExecutorService.controller;

import com.ExecutorService.ExecutorService.dto.CodeExecRequest;
import com.ExecutorService.ExecutorService.dto.CodeExecutionResults;
import com.ExecutorService.ExecutorService.service.CodeExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/execute")
public class ExecutorController {

    @Autowired
    private CodeExecutorService executorService;

    @PostMapping("/")
    public ResponseEntity<CodeExecutionResults> executeCode(@RequestBody CodeExecRequest req){
        return new ResponseEntity<>(executorService.executeCode(
                req.getCode(),
                req.getLang(),
                req.getInputs(),
                req.getExpectedOutputs()
        ), HttpStatus.OK);
    }
}
/*

{
        "lang": "java",
        "code": "import java.util.Scanner;class Solution{public static void main(String[] args){Scanner sc = new Scanner(System.in);int a = sc.nextInt();int b = sc.nextInt(); System.out.println(a + b);}}",
        "inputs": ["5 6", "5 5"],
        "expectedOutputs": ["11", "10"]
}

 */