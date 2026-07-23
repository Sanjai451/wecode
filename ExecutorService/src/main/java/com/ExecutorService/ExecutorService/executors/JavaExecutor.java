package com.ExecutorService.ExecutorService.executors;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class JavaExecutor implements CodeExecutor{

    @Override
    public boolean compile(Path workspace, String code) {
        try {
            // creating code files in execution Directory
            Path source = workspace.resolve("Solution.java");
            Files.write(source, code.getBytes());

            // Compile
            Process compile = new ProcessBuilder(
                    "docker",
                    "run",
                    "--rm",
                    "-v",
                    workspace.toAbsolutePath() + ":/app",
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

    @Override
    public String execute(Path workspace, String input) {
        try {

            Files.writeString(
                    workspace.resolve("input.txt"),
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
                    workspace.toAbsolutePath() + ":/app",
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
}
