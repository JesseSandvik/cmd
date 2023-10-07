package com.blckroot.cmdr.command;

interface ExecutableCommandContract extends CommandContract {
    String getExecutableFilePath();
}
