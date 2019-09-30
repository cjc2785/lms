package com.ss.lms.document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * Directory is a wrapper over the java folder handling APIs. A
 * Directory instance represents a folder. 
 * 
 */

public class Directory {

    private String path;

    public Directory(String dirPath) {
        path = dirPath;
    }

    public void createIfNotExists() throws IOException {
        Files.createDirectories(Paths.get(path));
    }
}