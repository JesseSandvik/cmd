package com.blckroot.cmd.filesystem;

import java.io.IOException;
import java.util.Properties;

public class FileSystemService implements FileSystemServiceContract {
    private final FileSystemServiceUtility fileSystemServiceUtility = new FileSystemServiceUtility();
    @Override
    public Boolean fileExists(String filePath) {
        return this.fileSystemServiceUtility.fileExists(filePath);
    }

    @Override
    public Boolean fileCanExecute(String filePath) {
        return this.fileSystemServiceUtility.fileCanExecute(filePath);
    }

    @Override
    public Properties getPropertiesFromPropertiesFile(String filePath) throws IOException {
        return this.fileSystemServiceUtility.getPropertiesFromPropertiesFile(filePath);
    }
}
