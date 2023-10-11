package com.blckroot.cmd;

import java.util.List;

interface PluginCommandContract extends CommandContract {
    String getExecutableFilePath();
    List<PluginCommand> getPluginSubcommands();
    void addPluginSubcommand(PluginCommand executableSubcommand);
}
