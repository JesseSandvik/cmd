package com.blckroot.commander;

import com.blckroot.commander.command.ExecutableCommand;
import picocli.CommandLine;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.Model.PositionalParamSpec;
import picocli.CommandLine.Model.OptionSpec;

import java.util.List;

class CommandLineParser {
    private final ExecutableCommand executableCommand;

    public CommandLineParser(ExecutableCommand executableCommand) {
        this.executableCommand = executableCommand;
    }

    public Integer parse(String[] arguments) throws Exception {
        CommandLineBuilder commandLineBuilder = new CommandLineBuilder(executableCommand).addStandardUsageHelpOption();

        if (executableCommand.getVersion() != null) {
            commandLineBuilder.addStandardVersionHelpOption();
        }

        CommandLine commandLine = commandLineBuilder.build();
        ParseResult parseResult = commandLine.parseArgs(arguments);
        return parseResults(parseResult, executableCommand, commandLine);
    }

    private Integer parseResults(ParseResult parseResult, ExecutableCommand executableCommand, CommandLine commandLine) throws Exception {
        boolean helpIsOnlyArgument = parseResult.expandedArgs().size() == 1 &&
                parseResult.expandedArgs().get(0).equalsIgnoreCase("help");

        if (commandLine.isUsageHelpRequested() || helpIsOnlyArgument) {
            return usageHelp(commandLine);
        }

        if (commandLine.isVersionHelpRequested()) {
            return versionHelp(commandLine);
        }

        parsePositionalParameters(parseResult, executableCommand);
        parseOptions(parseResult, executableCommand);

        if (parseResult.subcommands().isEmpty()) {
            return executableCommand.call();
        } else {
            parseSubcommands(parseResult, executableCommand, commandLine);
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

    private void parsePositionalParameters(ParseResult parseResult, ExecutableCommand executableCommand) {
        List<PositionalParamSpec> parsedPositionalParameters = parseResult.matchedPositionals();

        if (!parsedPositionalParameters.isEmpty()) {
            for (PositionalParamSpec parsedPositionalParameter : parsedPositionalParameters) {
                executableCommand.setPositionalParameterValueToParameterPosition(
                        Integer.valueOf(String.valueOf(parsedPositionalParameter.index())),
                        parsedPositionalParameter.getValue()
                );
            }
        }
    }

    private void parseOptions(ParseResult parseResult, ExecutableCommand executableCommand) {
        List<OptionSpec> parsedOptions = parseResult.matchedOptions();

        if (!parsedOptions.isEmpty()) {
            for (OptionSpec optionSpec : parsedOptions) {
                executableCommand.setOptionValueToOptionLongName(optionSpec.longestName(), optionSpec.getValue());
            }
        }
    }

    private void parseSubcommands(ParseResult parseResult, ExecutableCommand executableCommand, CommandLine commandLine) throws Exception {
        for (ParseResult subcommandParseResult : parseResult.subcommands()) {
            String subcommandName = subcommandParseResult.commandSpec().name();

            for (ExecutableCommand executableSubcommand : executableCommand.getExecutableSubcommands()) {
                if (executableSubcommand.getName().equalsIgnoreCase(subcommandName)) {
                    CommandLine subcommandLine = commandLine.getSubcommands().get(subcommandName);
                    parseResults(subcommandParseResult, executableSubcommand, subcommandLine);
                }
            }
        }
    }
}
