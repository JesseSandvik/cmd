package com.blckroot.commander;

import com.blckroot.commander.command.Command;
import com.blckroot.commander.command.ExecutableCommand;
import com.blckroot.commander.command.positionalParameter.PositionalParameter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CommanderTest {
    private Commander COMMANDER;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    public void setUpCommander(Command command) {
        COMMANDER = new Commander(new ExecutableCommand(command));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    //  | - HELP TESTS ----------------------------------------------------------------------------------------------- |
    @Test
    public void printsCommandUsageInformationForStandardUsageHelpOptionShort() throws Exception {
        Command command = new Command("testCommand");

        setUpCommander(command);
        int exitCode = COMMANDER.execute(new String[]{"-h"});

        assertEquals(exitCode, 0);
        assertTrue(outContent.toString().contains(command.getName()));
        assertTrue(outContent.toString().contains("-h"));
        assertTrue(outContent.toString().contains("--help"));
        assertFalse(outContent.toString().contains("-v"));
        assertFalse(outContent.toString().contains("--version"));
        assertEquals(errContent.toString(), "");
    }

    @Test
    public void printsCommandUsageInformationForStandardUsageHelpOptionLong() throws Exception {
        Command command = new Command("testCommand");

        setUpCommander(command);
        int exitCode = COMMANDER.execute(new String[]{"--help"});

        assertEquals(exitCode, 0);
        assertTrue(outContent.toString().contains(command.getName()));
        assertTrue(outContent.toString().contains("-h"));
        assertTrue(outContent.toString().contains("--help"));
        assertFalse(outContent.toString().contains("-v"));
        assertFalse(outContent.toString().contains("--version"));
        assertEquals(errContent.toString(), "");
    }
    //  | - VERSION TESTS -------------------------------------------------------------------------------------------- |
    @Test
    public void printsCommandUsageInformationForStandardUsageHelpOptionShortWithVersion() throws Exception {
        Command command = new Command("testCommand");
        command.setVersion("1.2.3");

        setUpCommander(command);
        int exitCode = COMMANDER.execute(new String[]{"-h"});

        assertEquals(exitCode, 0);
        assertTrue(outContent.toString().contains(command.getName()));
        assertTrue(outContent.toString().contains("-h"));
        assertTrue(outContent.toString().contains("--help"));
        assertTrue(outContent.toString().contains("-v"));
        assertTrue(outContent.toString().contains("--version"));
        assertEquals(errContent.toString(), "");
    }

    @Test
    public void printsCommandUsageInformationForStandardUsageHelpOptionLongWithVersion() throws Exception {
        Command command = new Command("testCommand");
        command.setVersion("1.2.3");

        setUpCommander(command);
        int exitCode = COMMANDER.execute(new String[]{"--help"});

        assertEquals(exitCode, 0);
        assertTrue(outContent.toString().contains(command.getName()));
        assertTrue(outContent.toString().contains("-h"));
        assertTrue(outContent.toString().contains("--help"));
        assertTrue(outContent.toString().contains("-v"));
        assertTrue(outContent.toString().contains("--version"));
        assertEquals(errContent.toString(), "");
    }

    @Test
    public void printsCommandVersionInformationForStandardVersionHelpOptionShort() throws Exception {
        Command command = new Command("testCommand");
        command.setVersion("1.2.3");

        setUpCommander(command);
        int exitCode = COMMANDER.execute(new String[]{"-v"});

        assertEquals(exitCode, 0);
        assertTrue(outContent.toString().contains(command.getVersion()));
        assertEquals(errContent.toString(), "");
    }

    @Test
    public void printsCommandVersionInformationForStandardVersionHelpOptionLong() throws Exception {
        Command command = new Command("testCommand");
        command.setVersion("1.2.3");

        setUpCommander(command);
        int exitCode = COMMANDER.execute(new String[]{"--version"});

        assertEquals(exitCode, 0);
        assertTrue(outContent.toString().contains(command.getVersion()));
        assertEquals(errContent.toString(), "");
    }
    //  | - PARAMETER TESTS ------------------------------------------------------------------------------------------ |
    @Test
    public void printsCommandUsageInformationForStandardUsageHelpOptionShortWithPositionalParameter() throws Exception {
        Command command = new Command("testCommand");
        PositionalParameter positionalParameter =
                new PositionalParameter("paramA", "Description for paramA");
        command.addPositionalParameter(positionalParameter);

        setUpCommander(command);
        int exitCode = COMMANDER.execute(new String[]{"-h"});

        assertEquals(exitCode, 0);
        assertTrue(outContent.toString().contains(command.getName()));
        assertTrue(outContent.toString().contains(positionalParameter.label()));
        assertTrue(outContent.toString().contains(positionalParameter.description()));
        assertTrue(outContent.toString().contains("-h"));
        assertTrue(outContent.toString().contains("--help"));
        assertFalse(outContent.toString().contains("-v"));
        assertFalse(outContent.toString().contains("--version"));
        assertEquals(errContent.toString(), "");
    }

    @Test
    public void printsCommandUsageInformationForStandardUsageHelpOptionLongWithPositionalParameter() throws Exception {
        Command command = new Command("testCommand");
        PositionalParameter positionalParameter =
                new PositionalParameter("paramA", "Description for paramA");
        command.addPositionalParameter(positionalParameter);

        setUpCommander(command);
        int exitCode = COMMANDER.execute(new String[]{"--help"});

        assertEquals(exitCode, 0);
        assertTrue(outContent.toString().contains(command.getName()));
        assertTrue(outContent.toString().contains(positionalParameter.label()));
        assertTrue(outContent.toString().contains(positionalParameter.description()));
        assertTrue(outContent.toString().contains("-h"));
        assertTrue(outContent.toString().contains("--help"));
        assertFalse(outContent.toString().contains("-v"));
        assertFalse(outContent.toString().contains("--version"));
        assertEquals(errContent.toString(), "");
    }
}
