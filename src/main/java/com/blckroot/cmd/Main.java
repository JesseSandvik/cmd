package com.blckroot.cmd;

public class Main {
    public static void main(String[] args) throws Exception {
        PluginCommand command = new PluginCommandBuilder("command", "src/main/resources/command")
                .propertiesFileDirectory("src/main/resources/")
                .build();

        command.setParentCommandName("parent");
        command.call();
        System.exit(0);
    }
}
