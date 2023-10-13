package com.blckroot.cmd;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class PluginCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    //  ***** Set Command Attributes ***********************************************************************************

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_name() {
        String expected = "test";
        PluginCommand pluginCommand = new PluginCommand(expected, "");

        String actual = pluginCommand.getName();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_version() {
        PluginCommand pluginCommand = new PluginCommand("test", "");
        String expected = "1.2.3";
        pluginCommand.setVersion(expected);

        String actual = pluginCommand.getVersion();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_usage_description_synopsis() {
        PluginCommand pluginCommand = new PluginCommand("test", "");
        String expected = "Test description synopsis.";
        pluginCommand.setUsageDescriptionSynopsis(expected);

        String actual = pluginCommand.getUsageDescriptionSynopsis();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_usage_description_full() {
        PluginCommand pluginCommand = new PluginCommand("test", "");
        String expected = "Test description full.";
        pluginCommand.setUsageDescriptionFull(expected);

        String actual = pluginCommand.getUsageDescriptionFull();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_executes_without_arguments() {
        PluginCommand pluginCommand = new PluginCommand("test", "");
        boolean expected = true;
        pluginCommand.executesWithoutArguments(expected);

        boolean actual = pluginCommand.executesWithoutArguments();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_positional_parameters() {
        PluginCommand pluginCommand = new PluginCommand("test", "");
        PositionalParameter positionalParameter = new PositionalParameter("", "");
        pluginCommand.addPositionalParameter(positionalParameter);
        boolean expected = false;

        boolean actual = pluginCommand.getPositionalParameters().isEmpty();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_options() {
        PluginCommand pluginCommand = new PluginCommand("test", "");
        Option option = new Option("", "");
        pluginCommand.addOption(option);
        boolean expected = false;

        boolean actual = pluginCommand.getOptions().isEmpty();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_executable_subcommands() {
        PluginCommand pluginCommand = new PluginCommand("test", "");
        PluginCommand pluginSubcommand = new PluginCommand("subtest", "");
        pluginCommand.addPluginSubcommand(pluginSubcommand);
        boolean expected = false;

        boolean actual = pluginCommand.getPluginSubcommands().isEmpty();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_executable_file_directory() {
        String expected = "/src/file/path/";
        PluginCommand pluginCommand = new PluginCommand("test", expected);

        String actual = pluginCommand.getExecutableFileDirectory();
        assertEquals(actual, expected);
    }

    //  ***** Call Command *********************************************************************************************

    @Test
    void EXECUTABLE_COMMAND_call_no_arguments() {
        PluginCommand pluginCommand = new PluginCommand("test", "");
        int expected = 0;

        int actual = pluginCommand.call();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_call_positional_parameter_executable_in_current_directory() {
        PluginCommand pluginCommand = new PluginCommand("echo", "src/test/resources/");
        PositionalParameter positionalParameter = new PositionalParameter("", "");
        String expected = "Hello, World!";
        positionalParameter.setValue(expected);
        pluginCommand.addPositionalParameter(positionalParameter);
        pluginCommand.call();

        assertTrue(outContent.toString().contains(expected));
    }

    @Test
    void EXECUTABLE_COMMAND_call_positional_parameter_executable_in_parent_directory() {
        PluginCommand pluginCommand = new PluginCommand("echoChild", "src/test/resources/");
        pluginCommand.setParentCommandName("echoParent");
        PositionalParameter positionalParameter = new PositionalParameter("", "");
        String expected = "Hello, World!";
        positionalParameter.setValue(expected);
        pluginCommand.addPositionalParameter(positionalParameter);
        pluginCommand.call();

        assertTrue(outContent.toString().contains(expected));
    }

    @Test
    void EXECUTABLE_COMMAND_call_option_boolean_executable_in_current_directory() {
        PluginCommand pluginCommand = new PluginCommand("echo", "src/test/resources/");
        Option option = new Option(new String[]{"-t", "--test"}, "");
        option.setValue(true);
        pluginCommand.addOption(option);
        String expected = option.getLongestName();
        pluginCommand.call();

        assertTrue(outContent.toString().contains(expected));
    }

    @Test
    void EXECUTABLE_COMMAND_call_option_boolean_executable_in_parent_directory() {
        PluginCommand pluginCommand = new PluginCommand("echoChild", "src/test/resources/");
        pluginCommand.setParentCommandName("echoParent");
        Option option = new Option(new String[]{"-t", "--test"}, "");
        option.setValue(true);
        pluginCommand.addOption(option);
        String expected = option.getLongestName();
        pluginCommand.call();

        assertTrue(outContent.toString().contains(expected));
    }

    @Test
    void EXECUTABLE_COMMAND_call_option_string_executable_in_current_directory() {
        PluginCommand pluginCommand = new PluginCommand("echo", "src/test/resources/");
        Option option = new Option(new String[]{"-t", "--test"}, "", "");
        String value = "Hello";
        option.setValue(value);
        pluginCommand.addOption(option);
        String expected = option.getLongestName() + " " + value;
        pluginCommand.call();

        assertTrue(outContent.toString().contains(expected));
    }

    @Test
    void EXECUTABLE_COMMAND_call_option_string_executable_in_parent_directory() {
        PluginCommand pluginCommand = new PluginCommand("echoChild", "src/test/resources/");
        pluginCommand.setParentCommandName("echoParent");
        Option option = new Option(new String[]{"-t", "--test"}, "", "");
        String value = "Hello";
        option.setValue(value);
        pluginCommand.addOption(option);
        String expected = option.getLongestName() + " " + value;
        pluginCommand.call();

        assertTrue(outContent.toString().contains(expected));
    }
}
