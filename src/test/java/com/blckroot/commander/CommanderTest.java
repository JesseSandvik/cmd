package com.blckroot.commander;

import com.blckroot.commander.command.Command;
import com.blckroot.commander.command.ExecutableCommand;
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

    @Test
    public void printsCommandVersionInformationForStandardVersionOptionShort() throws Exception {
        Command command = new Command("testCommand");
        command.setVersion("1.2.3");
        setUpCommander(command);
        int exitCode = COMMANDER.execute(new String[]{"-v"});

        assertEquals(exitCode, 0);
        assertTrue(outContent.toString().contains(command.getVersion()));
        assertEquals(errContent.toString(), "");
    }

    @Test
    public void printsCommandVersionInformationForStandardVersionOptionLong() throws Exception {
        Command command = new Command("testCommand");
        command.setVersion("1.2.3");
        setUpCommander(command);
        int exitCode = COMMANDER.execute(new String[]{"--version"});

        assertEquals(exitCode, 0);
        assertTrue(outContent.toString().contains(command.getVersion()));
        assertEquals(errContent.toString(), "");
    }
}
