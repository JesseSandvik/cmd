package com.blckroot.commander.command;

import com.blckroot.commander.command.option.Option;
import com.blckroot.commander.command.positionalParameter.PositionalParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ExecutableCommand extends ParsedCommand implements Callable<Integer> {
    private final List<ExecutableCommand> executableSubcommands = new ArrayList<>();
    public ExecutableCommand(Command command) {
        super(command.getName());

        if (command.getVersion() != null) {
            this.setVersion(command.getVersion());
        }

        if (command.getUsageDescriptionSynopsis() != null) {
            this.setUsageDescriptionSynopsis(command.getUsageDescriptionSynopsis());
        }

        if (command.getUsageDescriptionFull() != null) {
            this.setUsageDescriptionFull(command.getUsageDescriptionFull());
        }

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

        if (!command.getSubcommands().isEmpty()) {
            for (Command subcommand : command.getSubcommands()) {
                this.addSubcommand(subcommand);

                ExecutableCommand executableSubcommand = new ExecutableCommand(subcommand);
                this.executableSubcommands.add(executableSubcommand);
            }
        }
    }

    public List<ExecutableCommand> getExecutableSubcommands() {
        return executableSubcommands;
    }

    @Override
    public Integer call() throws Exception {
        return null;
    }
}
