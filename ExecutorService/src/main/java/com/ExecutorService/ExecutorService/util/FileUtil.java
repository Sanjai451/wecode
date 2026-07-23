package com.ExecutorService.ExecutorService.util;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.UUID;

@Service
public class FileUtil {
    public Path createExecutionDirectory(){
        try {
            //  creating execution directory
            Path root = Paths.get("code");

            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }

            String folderName = UUID.randomUUID().toString();
            Path executionDir = root.resolve(folderName);

            Files.createDirectories(executionDir);

            return executionDir;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    public void removeCodeFiles(Path executionDir) {
        try {
            System.out.println("Removing files : " + executionDir.toString());
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

}
