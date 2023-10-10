package com.blckroot.cmd.filesystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class FileSystemServiceUtility implements FileSystemServiceContract {
    @Override
    public Boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

    @Override
    public Boolean fileCanExecute(String filePath) {
        return new File(filePath).canExecute();
    }

    @Override
    public Properties getPropertiesFromPropertiesFile(String filePath) throws IOException {
        InputStream inputStream = null;
        Properties properties = null;
        try {
            inputStream = new FileInputStream(filePath);
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException exception) {
            throw new IOException(exception.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return properties;
    }
}
