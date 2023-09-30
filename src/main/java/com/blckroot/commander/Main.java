package com.blckroot.commander;

import com.blckroot.commander.command.Command;
import com.blckroot.commander.command.option.Option;
import com.blckroot.commander.picocli.CommandLineBuilder;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        Command command = new Command("testing");
        Command subcommand = new Command("testingsub");
        command.addOption(new Option("--optA", "This does something."));
        command.addOption(new Option(new String[]{"-a", "--apples"},  "This does something with apples."));
        command.addOption(new Option("--optC", "This does something.", "<something>", false));
        command.addSubcommand(subcommand);
        CommandLine commandLine = new CommandLineBuilder(command)
                .addStandardHelpOption()
                .addStandardVersionOption()
                .build();
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }
}