package com.blckroot.cmd;

public class Main {
    public static void main(String[] args) throws Exception {
        PluginCommand command = new PluginCommandBuilder("command", "")
                .propertiesFileDirectory("src/main/resources/")
                .build();

        System.exit(0);
    }
}
