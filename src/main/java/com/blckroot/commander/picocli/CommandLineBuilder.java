package com.blckroot.commander.picocli;

import com.blckroot.commander.command.Command;
import com.blckroot.commander.command.option.Option;
import com.blckroot.commander.command.positionalParameter.PositionalParameter;
import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.PositionalParamSpec;
import picocli.CommandLine.Model.OptionSpec;

public class CommandLineBuilder {
    private final Command command;
    private final CommandSpec commandSpec;

    public CommandLineBuilder(Command command) {
        this.command = command;
        this.commandSpec = CommandSpec.create().name(command.getName());

        if (command.getVersion() != null) {
            this.commandSpec.version(command.getVersion());
        }

        if (command.getUsageDescriptionSynopsis() != null) {
            this.commandSpec.usageMessage().description(command.getUsageDescriptionSynopsis());
        }

        if (command.getUsageDescriptionFull() != null) {
            this.commandSpec.usageMessage().footer("\n" + command.getUsageDescriptionFull() + "%n");
        }

        this.commandSpec.usageMessage()
                .autoWidth(true)
                .adjustLineBreaksForWideCJKCharacters(true)
                .abbreviateSynopsis(true)
                .parameterListHeading("\nParameters:%n")
                .optionListHeading("\nOptions:%n")
                .commandListHeading("\nCommands:%n");
    }

    public CommandLineBuilder addStandardHelpOption() {
        this.commandSpec.addOption(OptionSpec
                .builder("-h", "--help")
                .usageHelp(true)
                .description("Show this help message and exit.")
                .build());

        return this;
    }

    public CommandLineBuilder addStandardVersionOption() {
        this.commandSpec.addOption(OptionSpec
                .builder("-v", "--version")
                .versionHelp(true)
                .description("Print version information and exit.")
                .build());

        return this;
    }

    private void addPositionalParameter(PositionalParameter positionalParameter) {
        this.commandSpec.addPositional(PositionalParamSpec
                .builder()
                .paramLabel(positionalParameter.label())
                .description(positionalParameter.description())
                .build());
    }

    private void addOption(Option option) {
        OptionSpec.Builder optionSpecBuilder;
        if (option.getShortName() == null) {
            optionSpecBuilder = OptionSpec.builder(new String[]{option.getLongName(), option.getShortName()});
        } else {
            optionSpecBuilder = OptionSpec.builder(new String[]{option.getLongName()});
        }

        if (option.getParameterLabel() != null) {
            optionSpecBuilder.paramLabel(option.getParameterLabel());
        }

        optionSpecBuilder
                .description(option.getDescription())
                .type(option.isBoolean() ? Boolean.class : String.class);

        this.commandSpec.addOption(optionSpecBuilder.build());
    }

    public CommandLine build() {
        if (!command.getPositionalParameters().isEmpty()) {
            for (PositionalParameter positionalParameter : command.getPositionalParameters()) {
                this.addPositionalParameter(positionalParameter);
            }
        }

        if (!command.getOptions().isEmpty()) {
            for (Option option : command.getOptions()) {
                this.addOption(option);
            }
        }

        return new CommandLine(this.commandSpec);
    }
}
