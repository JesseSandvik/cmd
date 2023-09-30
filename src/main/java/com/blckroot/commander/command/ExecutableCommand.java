package com.blckroot.commander.command;

import java.util.concurrent.Callable;

public class ExecutableCommand extends ParsedCommand implements Callable<Integer> {
    public ExecutableCommand(String name) {
        super(name);
    }

    @Override
    public Integer call() throws Exception {
        return null;
    }
}
