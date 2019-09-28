package com.ss.lms.document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Directory {

    String path;

    public Directory(String dirPath) {
        path = dirPath;
    }

    public void createIfNotExists() throws IOException {
        Files.createDirectories(Paths.get(path));
    }
}