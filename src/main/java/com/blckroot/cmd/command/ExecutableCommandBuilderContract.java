package com.blckroot.cmd.command;

import com.blckroot.cmd.option.Option;
import com.blckroot.cmd.positionalParameter.PositionalParameter;

import java.io.IOException;
import java.util.Properties;

interface ExecutableCommandBuilderContract {
    ExecutableCommandBuilder setVersionPropertyKey(String versionPropertyKey);
    ExecutableCommandBuilder setSynopsisPropertyKey(String synopsisPropertyKey);
    ExecutableCommandBuilder setDescriptionPropertyKey(String descriptionPropertyKey);
    ExecutableCommandBuilder setExecutesWithoutArgumentsPropertyKey(String executesWithoutArgumentsPropertyKey);
    ExecutableCommandBuilder setPropertiesFilePath(String propertiesFilePath);
    ExecutableCommandBuilder setProperties(Properties properties);
    ExecutableCommandBuilder setVersion(String version);
    ExecutableCommandBuilder setUsageDescriptionSynopsis(String usageDescriptionSynopsis);
    ExecutableCommandBuilder setUsageDescriptionFull(String usageDescriptionFull);
    ExecutableCommandBuilder addPositionalParameter(PositionalParameter positionalParameter);
    ExecutableCommandBuilder addOption(Option option);
    ExecutableCommandBuilder addExecutableSubcommand(ExecutableCommand executableSubcommand);
    ExecutableCommand build() throws IOException;
}
