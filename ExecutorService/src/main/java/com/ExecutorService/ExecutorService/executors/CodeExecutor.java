package com.ExecutorService.ExecutorService.executors;

import java.nio.file.Path;

public interface CodeExecutor {

    boolean compile(Path workspace, String code);

    String execute(Path workspace, String input);

}

