package com.ss.lms.document;

import java.util.List;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.StandardOpenOption;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Document {

	private String path;


	public Document(String docPath) {
		path = docPath;
	}
	
	public void createIfNotExists() throws IOException {
		try {
			Files.createFile(Paths.get(path));
		} catch(FileAlreadyExistsException e) {
			
		}
	}

	public List<String> getLines() throws IOException {



		List<String> lines = Files.readAllLines(
				Paths.get(path), 
				StandardCharsets.UTF_8
				);
		return lines;
	}

	public void save(List<String> lines) throws IOException {


		Files.write(
				Paths.get(path),
				lines,
				StandardCharsets.UTF_8,
				StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING
				);
	}



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