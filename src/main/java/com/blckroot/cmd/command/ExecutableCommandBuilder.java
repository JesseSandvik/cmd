package com.blckroot.cmd.command;

import com.blckroot.cmd.filesystem.FileSystemService;
import com.blckroot.cmd.option.Option;
import com.blckroot.cmd.positionalParameter.PositionalParameter;

import java.io.IOException;
import java.util.Properties;

public class ExecutableCommandBuilder implements ExecutableCommandBuilderContract {
    private final ExecutableCommand executableCommand;
    private String propertiesFilePath;
    private Properties properties;
    private String versionPropertyKey = "version";
    private String synopsisPropertyKey = "synopsis";
    private String descriptionPropertyKey = "description";
    private String executesWithoutArgumentsPropertyKey = "executes.without.arguments";

    public ExecutableCommandBuilder(String name, String executableFilePath) {
        this.executableCommand = new ExecutableCommand(name, executableFilePath);
    }

    @Override
    public ExecutableCommandBuilder setVersionPropertyKey(String versionPropertyKey) {
        this.versionPropertyKey = versionPropertyKey;
        return this;
    }

    @Override
    public ExecutableCommandBuilder setSynopsisPropertyKey(String synopsisPropertyKey) {
        this.synopsisPropertyKey = synopsisPropertyKey;
        return this;
    }

    @Override
    public ExecutableCommandBuilder setDescriptionPropertyKey(String descriptionPropertyKey) {
        this.descriptionPropertyKey = descriptionPropertyKey;
        return this;
    }

    @Override
    public ExecutableCommandBuilder setExecutesWithoutArgumentsPropertyKey(String executesWithoutArgumentsPropertyKey) {
        this.executesWithoutArgumentsPropertyKey = executesWithoutArgumentsPropertyKey;
        return this;
    }

    @Override
    public ExecutableCommandBuilder setPropertiesFilePath(String propertiesFilePath) {
        this.propertiesFilePath = propertiesFilePath;
        this.properties = null;

        return this;
    }

    @Override
    public ExecutableCommandBuilder setProperties(Properties properties) {
        this.propertiesFilePath = null;
        this.properties = properties;

        return this;
    }

    @Override
    public ExecutableCommandBuilder setVersion(String version) {
        this.executableCommand.setVersion(version);
        return this;
    }

    @Override
    public ExecutableCommandBuilder setUsageDescriptionSynopsis(String usageDescriptionSynopsis) {
        this.executableCommand.setUsageDescriptionSynopsis(usageDescriptionSynopsis);
        return this;
    }

    @Override
    public ExecutableCommandBuilder setUsageDescriptionFull(String usageDescriptionFull) {
        this.executableCommand.setUsageDescriptionFull(usageDescriptionFull);
        return this;
    }

    @Override
    public ExecutableCommandBuilder addPositionalParameter(PositionalParameter positionalParameter) {
        this.executableCommand.addPositionalParameter(positionalParameter);
        return this;
    }

    @Override
    public ExecutableCommandBuilder addOption(Option option) {
        this.executableCommand.addOption(option);
        return this;
    }

    @Override
    public ExecutableCommandBuilder addExecutableSubcommand(ExecutableCommand executableSubcommand) {
        this.executableCommand.addExecutableSubcommand(executableSubcommand);
        return this;
    }

    @Override
    public ExecutableCommand build() throws IOException {
        if (propertiesFilePath != null) {
            FileSystemService fileSystemService = new FileSystemService();
            properties = fileSystemService.getPropertiesFromPropertiesFile(propertiesFilePath);
        }

        if (properties != null) {
            setExecutableCommandAttributes(properties);
        }

        return executableCommand;
    }

    private void setExecutableCommandAttributes(Properties properties) {
        if (properties.getProperty(versionPropertyKey) != null) {
            executableCommand.setVersion(properties.getProperty(versionPropertyKey));
        }

        if (properties.getProperty(synopsisPropertyKey) != null) {
            executableCommand.setUsageDescriptionSynopsis(properties.getProperty(synopsisPropertyKey));
        }

        if (properties.getProperty(descriptionPropertyKey) != null) {
            executableCommand.setUsageDescriptionFull(properties.getProperty(descriptionPropertyKey));
        }

        if (properties.getProperty(executesWithoutArgumentsPropertyKey) != null) {
            executableCommand.executesWithoutArguments(
                    Boolean.valueOf(
                            properties.getProperty(executesWithoutArgumentsPropertyKey))
            );
        }
    }
}
