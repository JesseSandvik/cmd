package com.blckroot.cmd;

import com.blckroot.cmd.filesystem.FileSystemService;

import java.io.IOException;
import java.util.*;

public class PluginCommandBuilder implements PluginCommandBuilderContract {
    private final PluginCommand pluginCommand;
    private String propertiesFileDirectory;
    private Properties properties;

    public PluginCommandBuilder(String commandName, String executableFilePath) {
        this.pluginCommand = new PluginCommand(commandName, executableFilePath);
    }

    @Override
    public PluginCommandBuilder propertiesFileDirectory(String propertiesFileDirectory) {
        this.propertiesFileDirectory = propertiesFileDirectory;
        this.properties = null;

        return this;
    }

    @Override
    public PluginCommandBuilder properties(Properties properties) {
        this.properties = properties;
        this.propertiesFileDirectory = null;

        return this;
    }

    @Override
    public PluginCommand build() throws IOException {
        if (propertiesFileDirectory != null) {
            String commandPropertiesFilePath = propertiesFileDirectory + pluginCommand.getName() + ".properties";
            FileSystemService fileSystemService = new FileSystemService();
            properties = fileSystemService.getPropertiesFromPropertiesFile(commandPropertiesFilePath);
        }

        if (properties != null) {
            System.out.println("Setting properties.");
            setPluginCommandAttributes(properties);
            setPluginCommandPositionalParameters(properties);
            setPluginCommandOptions(properties);
            buildPluginCommandPluginSubcommands(properties);
        }

        return pluginCommand;
    }

    private void setPluginCommandAttributes(Properties properties) {
        final String VERSION_PROPERTY_KEY="version";
        final String SYNOPSIS_PROPERTY_KEY="synopsis";
        final String DESCRIPTION_PROPERTY_KEY="description";
        final String EXECUTES_WITHOUT_ARGUMENTS_PROPERTY_KEY="executes.without.arguments";

        if (properties.getProperty(VERSION_PROPERTY_KEY) != null) {
            this.pluginCommand.setVersion(properties.getProperty(VERSION_PROPERTY_KEY));
        }

        if (properties.getProperty(SYNOPSIS_PROPERTY_KEY) != null) {
            this.pluginCommand.setUsageDescriptionSynopsis(properties.getProperty(SYNOPSIS_PROPERTY_KEY));
        }

        if (properties.getProperty(DESCRIPTION_PROPERTY_KEY) != null) {
            this.pluginCommand.setUsageDescriptionFull(properties.getProperty(DESCRIPTION_PROPERTY_KEY));
        }

        if (properties.getProperty(EXECUTES_WITHOUT_ARGUMENTS_PROPERTY_KEY) != null) {
            this.pluginCommand
                    .executesWithoutArguments(Boolean.valueOf(
                            properties.getProperty(EXECUTES_WITHOUT_ARGUMENTS_PROPERTY_KEY)));
        }
    }
    private void setPluginCommandPositionalParameters(Properties properties) {
        final String POSITIONAL_PARAMETER_LABELS_PROPERTY_KEY="positional.parameter.labels";
        final String POSITIONAL_PARAMETER_DESCRIPTION_PROPERTY_KEY="positional.parameter.description";

        if (properties.getProperty(POSITIONAL_PARAMETER_LABELS_PROPERTY_KEY) != null) {
            String[] positionalParameterLabels =
                    properties.getProperty(POSITIONAL_PARAMETER_LABELS_PROPERTY_KEY).split(",");
            for (int i = 0; i < positionalParameterLabels.length; i++) {
                PositionalParameter positionalParameter = new PositionalParameter(
                        positionalParameterLabels[i],
                        properties.getProperty(POSITIONAL_PARAMETER_DESCRIPTION_PROPERTY_KEY + "." + i)
                );
                this.pluginCommand.addPositionalParameter(positionalParameter);
            }
        }
    }
    private void setPluginCommandOptions(Properties properties) {
        final String OPTION_LONG_NAMES_PROPERTY_KEY="option.long.names";
        final String OPTION_SHORT_NAME_PROPERTY_KEY="option.short.name";
        final String OPTION_DESCRIPTION_PROPERTY_KEY="option.description";
        final String OPTION_PARAMETER_LABEL_PROPERTY_KEY="option.parameter.label";

        if (properties.getProperty(OPTION_LONG_NAMES_PROPERTY_KEY) != null) {
            String[] optionLongNames = properties.getProperty(OPTION_LONG_NAMES_PROPERTY_KEY).split(",");
            for (int i = 0; i < optionLongNames.length; i++) {
                Option option;

                if (properties.getProperty(OPTION_SHORT_NAME_PROPERTY_KEY + "." + i) != null) {
                    option = new Option(new String[]{
                            optionLongNames[i],
                            properties.getProperty(OPTION_SHORT_NAME_PROPERTY_KEY + "." + i)
                    },
                            properties.getProperty(OPTION_DESCRIPTION_PROPERTY_KEY + "." + i));
                } else {
                    option = new Option(
                            optionLongNames[i],
                            properties.getProperty(OPTION_DESCRIPTION_PROPERTY_KEY + "." + i));
                }

                if (properties.getProperty(OPTION_PARAMETER_LABEL_PROPERTY_KEY) != null) {
                    option.setParameterLabel(properties.getProperty(OPTION_PARAMETER_LABEL_PROPERTY_KEY + "." + i));
                }

                pluginCommand.addOption(option);
            }
        }
    }
    private void buildPluginCommandPluginSubcommands(Properties properties) {}
}
