package com.blckroot.commander;

import com.blckroot.commander.command.Command;
import com.blckroot.commander.picocli.CommandLineParser;

public class Main {
    public static void main(String[] args) {
        Command command = new Command("testing");

        CommandLineParser commandLineParser = new CommandLineParser(command);

        int exitCode = commandLineParser.parse(args);
        System.exit(exitCode);
    }
}