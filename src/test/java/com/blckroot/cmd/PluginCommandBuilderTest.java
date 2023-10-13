package com.blckroot.cmd;

import com.blckroot.cmd.filesystem.FileSystemService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PluginCommandBuilderTest {
    @Test
    void PLUGIN_COMMAND_BUILDER_ATTRIBUTES_SET_version() throws IOException {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromPropertiesFile("src/test/resources/test.properties");
        String expected = properties.getProperty("version");

        PluginCommand pluginCommand = new PluginCommandBuilder("test", "")
                .propertiesFileDirectory("src/test/resources/")
                .build();

        String actual = pluginCommand.getVersion();
        assertEquals(actual, expected);
    }

    @Test
    void PLUGIN_COMMAND_BUILDER_ATTRIBUTES_SET_synopsis() throws IOException {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromPropertiesFile("src/test/resources/test.properties");
        String expected = properties.getProperty("synopsis");

        PluginCommand pluginCommand = new PluginCommandBuilder("test", "")
                .propertiesFileDirectory("src/test/resources/")
                .build();

        String actual = pluginCommand.getUsageDescriptionSynopsis();
        assertEquals(actual, expected);
    }

    @Test
    void PLUGIN_COMMAND_BUILDER_ATTRIBUTES_SET_description() throws IOException {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromPropertiesFile("src/test/resources/test.properties");
        String expected = properties.getProperty("description");

        PluginCommand pluginCommand = new PluginCommandBuilder("test", "")
                .propertiesFileDirectory("src/test/resources/")
                .build();

        String actual = pluginCommand.getUsageDescriptionFull();
        assertEquals(actual, expected);
    }

    @Test
    void PLUGIN_COMMAND_BUILDER_ATTRIBUTES_SET_executes_without_arguments() throws IOException {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromPropertiesFile("src/test/resources/test.properties");
        boolean expected = Boolean.parseBoolean(properties.getProperty("executes.without.arguments"));

        PluginCommand pluginCommand = new PluginCommandBuilder("test", "")
                .propertiesFileDirectory("src/test/resources/")
                .build();

        boolean actual = pluginCommand.executesWithoutArguments();
        assertEquals(actual, expected);
    }

    @Test
    void PLUGIN_COMMAND_BUILDER_ATTRIBUTES_SET_parent_command_name() throws IOException {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromPropertiesFile("src/test/resources/test.properties");
        String expected = properties.getProperty("parent.command");

        PluginCommand pluginCommand = new PluginCommandBuilder("test", "")
                .propertiesFileDirectory("src/test/resources/")
                .build();

        String actual = pluginCommand.getParentCommandName();
        assertEquals(actual, expected);
    }
}
