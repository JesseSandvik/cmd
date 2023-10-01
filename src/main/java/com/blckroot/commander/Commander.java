package com.blckroot.commander;

import com.blckroot.commander.command.ExecutableCommand;

public class Commander {
    private final CommandLineParser COMMAND_LINE_PARSER;

    public Commander(ExecutableCommand executableCommand) {
        this.COMMAND_LINE_PARSER = new CommandLineParser(executableCommand);
    }

    public Integer execute(String[] args) throws Exception {
        return this.COMMAND_LINE_PARSER.parse(args);
    }
}
