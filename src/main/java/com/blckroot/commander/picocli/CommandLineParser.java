package com.blckroot.commander.picocli;

import com.blckroot.commander.command.Command;
import picocli.CommandLine;
import picocli.CommandLine.ParseResult;

public class CommandLineParser {
    private final Command rootCommand;

    public CommandLineParser(Command rootCommand) {
        this.rootCommand = rootCommand;
    }

    public Integer parse(String[] arguments) {
        CommandLine commandLine = new CommandLineBuilder(rootCommand)
                .addStandardUsageHelpOption()
                .addStandardVersionHelpOption()
                .build();

        ParseResult parseResult = commandLine.parseArgs(arguments);
        return parseResultHandler(parseResult, rootCommand, commandLine);
    }

    private Integer parseResultHandler(ParseResult parseResult, Command command, CommandLine commandLine) {
        boolean helpIsOnlyArgument = parseResult.expandedArgs().size() == 1 &&
                parseResult.expandedArgs().get(0).equalsIgnoreCase("help");

        if (commandLine.isUsageHelpRequested() || helpIsOnlyArgument) {
            return usageHelp(commandLine);
        }

        if (commandLine.isVersionHelpRequested()) {
            return versionHelp(commandLine);
        }

        return 0;
    }

    private Integer usageHelp(CommandLine commandLine) {
        commandLine.usage(commandLine.getOut());
        return commandLine.getCommandSpec().exitCodeOnUsageHelp();
    }

    private Integer versionHelp(CommandLine commandLine) {
        commandLine.printVersionHelp(commandLine.getOut());
        return commandLine.getCommandSpec().exitCodeOnVersionHelp();
    }

    private void parsePositionalParameters(ParseResult parseResult, Command command) {}

    private void parseOptions(ParseResult parseResult, Command command) {}
}
