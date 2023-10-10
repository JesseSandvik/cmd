package com.blckroot.cmd.filesystem;

import java.io.IOException;
import java.util.Properties;

interface FileSystemServiceContract {
    Boolean fileExists(String filePath);
    Boolean fileCanExecute(String filePath);
    Properties getPropertiesFromPropertiesFile(String filePath) throws IOException;
}
