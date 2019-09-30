package com.ss.lms.document;

import java.util.List;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.StandardOpenOption;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * Document is a wrapper over the java file handling APIs. A
 * Document instance represents a text file. 
 * 
 */

public class Document {

	private String path;


	public Document(String docPath) {
		path = docPath;
	}
	
	public void createIfNotExists() throws IOException {
		try {
			Files.createFile(Paths.get(path));
		} catch(FileAlreadyExistsException e) {
			//Do nothing if the file already exists
		}
	}

	public List<String> getLines() throws IOException {

		List<String> lines = Files.readAllLines(
				Paths.get(path), 
				StandardCharsets.UTF_8
				);
		return lines;
	}
	
	//Creates the file if it does not exist
	public void truncate() throws IOException {
		save(List.of());
	}

	
	//Overwrites the file
	//Creates the file if it does not exist
	public void save(List<String> lines) throws IOException {


		Files.write(
				Paths.get(path),
				lines,
				StandardCharsets.UTF_8,
				StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING
				);
	}


	//Creates the file if it does not exist
	public void append(List<String> lines) throws IOException {

		Files.write(
				Paths.get(path),
				lines,
				StandardCharsets.UTF_8,
				StandardOpenOption.CREATE,
				StandardOpenOption.APPEND
				);

	}
}