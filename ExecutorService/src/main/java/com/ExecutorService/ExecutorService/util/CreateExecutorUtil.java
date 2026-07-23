package com.ExecutorService.ExecutorService.util;

import com.ExecutorService.ExecutorService.executors.CodeExecutor;
import com.ExecutorService.ExecutorService.executors.JavaExecutor;
import com.ExecutorService.ExecutorService.executors.PythonExecutor;

public class CreateExecutorUtil {
    public static CodeExecutor create(String lang){
        if(lang.toLowerCase().trim().equals("python")){
            return new PythonExecutor();
        }
        else if(lang.toLowerCase().trim().equals("java")){
            return new JavaExecutor();
        }
        else{
            return null;
        }
    }
}
