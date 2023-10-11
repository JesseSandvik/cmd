package com.blckroot.cmd.command;

import com.blckroot.cmd.PluginCommand;
import com.blckroot.cmd.Option;
import com.blckroot.cmd.PositionalParameter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ExecutableCommandTest {
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
        PluginCommand executableCommand = new PluginCommand(expected, "");

        String actual = executableCommand.getName();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_version() {
        PluginCommand executableCommand = new PluginCommand("test", "");
        String expected = "1.2.3";
        executableCommand.setVersion(expected);

        String actual = executableCommand.getVersion();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_usage_description_synopsis() {
        PluginCommand executableCommand = new PluginCommand("test", "");
        String expected = "Test description synopsis.";
        executableCommand.setUsageDescriptionSynopsis(expected);

        String actual = executableCommand.getUsageDescriptionSynopsis();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_usage_description_full() {
        PluginCommand executableCommand = new PluginCommand("test", "");
        String expected = "Test description full.";
        executableCommand.setUsageDescriptionFull(expected);

        String actual = executableCommand.getUsageDescriptionFull();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_executes_without_arguments() {
        PluginCommand executableCommand = new PluginCommand("test", "");
        boolean expected = true;
        executableCommand.executesWithoutArguments(expected);

        boolean actual = executableCommand.executesWithoutArguments();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_positional_parameters() {
        PluginCommand executableCommand = new PluginCommand("test", "");
        PositionalParameter positionalParameter = new PositionalParameter("", "");
        executableCommand.addPositionalParameter(positionalParameter);
        boolean expected = false;

        boolean actual = executableCommand.getPositionalParameters().isEmpty();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_options() {
        PluginCommand executableCommand = new PluginCommand("test", "");
        Option option = new Option("", "");
        executableCommand.addOption(option);
        boolean expected = false;

        boolean actual = executableCommand.getOptions().isEmpty();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_executable_subcommands() {
        PluginCommand executableCommand = new PluginCommand("test", "");
        PluginCommand executableSubcommand = new PluginCommand("subtest", "");
        executableCommand.addExecutableSubcommand(executableSubcommand);
        boolean expected = false;

        boolean actual = executableCommand.getExecutableSubcommands().isEmpty();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_executable_file_path() {
        String expected = "/src/file/path";
        PluginCommand executableCommand = new PluginCommand("test", expected);

        String actual = executableCommand.getExecutableFilePath();
        assertEquals(actual, expected);
    }

    //  ***** Call Command *********************************************************************************************

    @Test
    void EXECUTABLE_COMMAND_call_no_arguments() throws Exception {
        PluginCommand executableCommand = new PluginCommand("test", "");
        int expected = 0;

        int actual = executableCommand.call();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_call_positional_parameter() throws Exception {
        PluginCommand executableCommand = new PluginCommand("echo", "src/test/resources/echo");
        PositionalParameter positionalParameter = new PositionalParameter("", "");
        String expected = "Hello, World!";
        positionalParameter.setValue(expected);
        executableCommand.addPositionalParameter(positionalParameter);
        executableCommand.call();

        assertTrue(outContent.toString().contains(expected));
    }

    @Test
    void EXECUTABLE_COMMAND_call_option_boolean() throws Exception {
        PluginCommand executableCommand = new PluginCommand("echo", "src/test/resources/echo");
        Option option = new Option(new String[]{"-t", "--test"}, "");
        option.setValue(true);
        executableCommand.addOption(option);
        String expected = option.getLongestName();
        executableCommand.call();

        assertTrue(outContent.toString().contains(expected));
    }

    @Test
    void EXECUTABLE_COMMAND_call_option_string() throws Exception {
        PluginCommand executableCommand = new PluginCommand("echo", "src/test/resources/echo");
        Option option = new Option(new String[]{"-t", "--test"}, "", "");
        String value = "Hello";
        option.setValue(value);
        executableCommand.addOption(option);
        String expected = option.getLongestName() + " " + value;
        executableCommand.call();

        assertTrue(outContent.toString().contains(expected));
    }
}
