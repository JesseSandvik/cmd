package com.blckroot.commander;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class ExecutableCommand extends Command implements Callable<Integer> {
    private final Map<String, Object> positionalParameters = new LinkedHashMap<>();
    private final Map<String, Object> options = new LinkedHashMap<>();
    public ExecutableCommand(String name) {
        super(name);
    }

    @Override
    public Integer call() throws Exception {
        return null;
    }
}
