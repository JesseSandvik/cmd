package com.blckroot.cmd;

import java.io.IOException;
import java.util.Properties;

interface PluginCommandBuilderContract {
    PluginCommandBuilder propertiesFileDirectory(String propertiesFileDirectory);
    PluginCommandBuilder properties(Properties properties);
    PluginCommand build() throws IOException;
}
