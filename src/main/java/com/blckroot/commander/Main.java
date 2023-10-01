package com.blckroot.commander;

import com.blckroot.commander.command.Command;
import com.blckroot.commander.command.ExecutableCommand;
import com.blckroot.commander.command.option.Option;
import com.blckroot.commander.command.positionalParameter.PositionalParameter;
import com.blckroot.commander.picocli.CommandLineParser;

public class Main {
    public static void main(String[] args) throws Exception {
        Command command = new Command("testing");
        Command subcommand = new Command("ABCD");
        subcommand.addOption(new Option(new String[]{"-c", "--candy"}, "I like candy.", "<param>", false));
        subcommand.addPositionalParameter(new PositionalParameter("HELLO", "Says hello."));
        subcommand.addPositionalParameter(new PositionalParameter("HELLO2", "Says hello."));
        subcommand.addPositionalParameter(new PositionalParameter("HELLO3", "Says hello."));
        command.addSubcommand(subcommand);
        ExecutableCommand executableCommand = new ExecutableCommand(command);
        CommandLineParser commandLineParser = new CommandLineParser(executableCommand);

        int exitCode = commandLineParser.parse(args);
        System.exit(exitCode);
    }
}