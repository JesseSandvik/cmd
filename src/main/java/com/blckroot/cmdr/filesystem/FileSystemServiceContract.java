package com.blckroot.cmdr.filesystem;

interface FileSystemServiceContract {
    Boolean fileExists(String filePath);
    Boolean fileCanExecute(String filePath);
}
