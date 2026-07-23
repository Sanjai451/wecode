package com.ExecutorService.ExecutorService.executors;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class PythonExecutor implements CodeExecutor {

    @Override
    public boolean compile(Path workspace, String code) {
        try {
            // creating code files in execution Directory
            Path source = workspace.resolve("Solution.py");
            Files.write(source, code.getBytes());
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
                    "pythona-code-runner",
                    "bash",
                    "-c",
                    "cd /app && python Solution.py < input.txt"
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