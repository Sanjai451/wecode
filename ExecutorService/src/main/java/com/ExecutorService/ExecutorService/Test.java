package com.ExecutorService.ExecutorService;

import com.ExecutorService.ExecutorService.dto.CodeExecutionResults;
import com.ExecutorService.ExecutorService.executors.CodeExecutor;
import com.ExecutorService.ExecutorService.executors.JavaExecutor;
import com.ExecutorService.ExecutorService.executors.PythonExecutor;
import com.ExecutorService.ExecutorService.util.CreateExecutorUtil;
import com.ExecutorService.ExecutorService.util.FileUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        String code = """
                import java.util.Scanner;
                class Solution{
                    public static void main(String[] args){
                        Scanner sc = new Scanner(System.in);
                        int a = sc.nextInt();
                        int b = sc.nextInt();
                        System.out.println(a + b);
                    }
                }
                """;

        String pyCode = """
                s = input()
                a = int(s.split(" ")[0])
                b = int(s.split(" ")[1])
                print(a + b)
                """;
        // inputs and outputs for addition of two numbers program
        ArrayList<String> inputs = new ArrayList<>();
        ArrayList<String> expectedOutputs = new ArrayList<>();
        inputs.add("5 10");
        inputs.add("5 1");
        inputs.add("5 0");
        expectedOutputs.add("15");
        expectedOutputs.add("6");
        expectedOutputs.add("5");

        CodeExecutionResults res = new Test().executeCode(pyCode, "python", inputs, expectedOutputs);

        System.out.println("resp : " + res.getMessage());
    }

    public CodeExecutionResults executeCode(String code, String lang, ArrayList<String> inputs, ArrayList<String> expectedOutputs) {
        FileUtil fileUtility = new FileUtil();

        try {

            Path executionDir = fileUtility.createExecutionDirectory();

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

            fileUtility.removeCodeFiles(executionDir);

            return new CodeExecutionResults(inputs.size(), 0, "Code Passed all Test Cases");

        } catch (Exception e) {
            return new CodeExecutionResults(-1, -1, e.getMessage());
        }
    }

    public static boolean writeCodeToFileAndCompile(String code, Path executionDir) {
        try {
            // creating code files in execution Directory
            Path source = executionDir.resolve("Solution.java");
            Files.write(source, code.getBytes());

            // Compile
            Process compile = new ProcessBuilder(
                    "docker",
                    "run",
                    "--rm",
                    "-v",
                    executionDir.toAbsolutePath() + ":/app",
                    "java-code-runner",
                    "bash",
                    "-c",
                    "cd /app && javac Solution.java"
            )
            .redirectErrorStream(true)
            .start();

            BufferedReader compilerReader = new BufferedReader(
                    new InputStreamReader(compile.getInputStream())
            );

            StringBuilder compileOutput = new StringBuilder();

            String line;
            while ((line = compilerReader.readLine()) != null) {
                compileOutput.append(line).append("\n");
            }

            int exitCode = compile.waitFor();

            // if exitCode == 0 -> Code compiled successfully, exitCode == 1 -> Code Compilation failed
            if (exitCode != 0) {
                System.out.println("Compilation Error:\n" + compileOutput);
                return false;
            }

            compile.waitFor();

            System.out.println("code compiled successfully : " + compile);

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static void removeCodeFiles(Path executionDir) {
        try {
            // Removing code files
            Files.walk(executionDir)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            System.out.println("Problem in removing Execution Code Files : " + e);
        }
    }

    public static String executeCode(String input, Path executionDir) {
        try {

            Files.writeString(
                    executionDir.resolve("input.txt"),
                    input
            );

            // Execute
            Process process = new ProcessBuilder(
                    "docker",
                    "run",
                    "--rm",
                    "--network", "none",
                    "--memory", "128m",
                    "--cpus", "1",
                    "--pids-limit", "50",
                    "-v",
                    executionDir.toAbsolutePath() + ":/app",
                    "java-code-runner",
                    "bash",
                    "-c",
                    "cd /app && java Solution < input.txt"
            )
                    .redirectErrorStream(true)
                    .start();

            // TLE Check
            boolean finished = process.waitFor(3, java.util.concurrent.TimeUnit.SECONDS);

            if (!finished) {
                process.destroyForcibly();
                return "Time Limit Exceeded";
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder output = new StringBuilder();

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            return output.toString().trim();

        } catch (Exception e) {
            System.out.println(e);
            return e.getMessage();
        }
    }

    // ------------ OLD ---------------
    public static String executeCode1(String input, Path executionDir) {
        try {
            // Execute
            Process process = new ProcessBuilder("java", "Solution")
                    .directory(executionDir.toFile())
                    .redirectErrorStream(true)
                    .start();

            // giving input
            BufferedWriter writer =
                    new BufferedWriter(
                            new OutputStreamWriter(process.getOutputStream()));

            writer.write(input);
            writer.flush();
            writer.close();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder output = new StringBuilder();

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();

            return output.toString().trim();
        } catch (Exception e) {
            System.out.println(e);
            return e.getMessage();
        }
    }

    public static boolean writeCodeToFileAndCompileO(String code, Path executionDir) {
        try {
            // creating code files in execution Directory
            Path source = executionDir.resolve("Solution.java");
            Files.write(source, code.getBytes());

            // Compile
            Process compile = new ProcessBuilder("javac", "Solution.java")
                    .directory(executionDir.toFile())
                    .redirectErrorStream(true)
                    .start();

            BufferedReader compilerReader = new BufferedReader(
                    new InputStreamReader(compile.getInputStream())
            );

            StringBuilder compileOutput = new StringBuilder();

            String line;
            while ((line = compilerReader.readLine()) != null) {
                compileOutput.append(line).append("\n");
            }

            int exitCode = compile.waitFor();

            // if exitCode == 0 -> Code compiled successfully, exitCode == 1 -> Code Compilation failed
            if (exitCode != 0) {
                System.out.println("Compilation Error:\n" + compileOutput);
                return false;
            }

            compile.waitFor();

            System.out.println("code compiled successfully : " + compile);

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


}

