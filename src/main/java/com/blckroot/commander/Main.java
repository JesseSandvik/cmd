package com.blckroot.commander;

import com.blckroot.commander.command.Command;
import com.blckroot.commander.picocli.CommandLineBuilder;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        Command command = new Command("testing");
        CommandLine commandLine = new CommandLineBuilder(command)
                .addStandardHelpOption()
                .addStandardVersionOption()
                .build();
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }
}