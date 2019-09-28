package com.ss.lms.document;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;


public class DelimitedDoc {

    private Document doc;
    private final String delimiter = "\\|";

    public DelimitedDoc(String path) {
        doc = new Document(path);
    }

    
    public Optional<List<String>> getLine(int id) throws IOException {

        String idString = Integer.toString(id);
        List<List<String>> lines = getLines();
      

        List<List<String>> match = lines.stream()
            .filter(line -> line.get(0).equals(idString))
            .collect(Collectors.toList());

        return match.isEmpty() ? 
            Optional.empty() : 
            Optional.of(match.get(0));
    }

    public List<List<String>> getLines() throws IOException {
        List<String> lines = doc.getLines();
        return lines.stream()
            .map(line -> line.split(delimiter, -1))
            .map(Arrays::asList)
            .collect(Collectors.toList());
    }

    

    //First field is used as id
    public void updateLine(List<String> fields) throws IOException {

        String id = fields.get(0);

        List<List<String>> lines = getLines();

        List<List<String>> newLines = lines.stream()
            .map(line -> line.get(0).equals(id) ? fields : line)
            .collect(Collectors.toList());
        
        save(newLines);
    }

    public void deleteLine(List<String> fields) throws IOException {

        String id = fields.get(0);
        List<List<String>> lines = getLines();

        List<List<String>> newLines = lines.stream()
            .filter(line -> !line.get(0).equals(id))
            .collect(Collectors.toList());

        
        save(newLines);
    }

    public void save(List<List<String>> lines) throws IOException {

        List<String> newLines = lines.stream()
            .map( line -> String.join(
                "|", line
            ))
            .collect(Collectors.toList());
  
        doc.save(newLines);
    }

    public void appendLine(List<String> line) throws IOException {
        append(List.of(line));
    }

    public void append(List<List<String>> lines) throws IOException {

        List<String> newLines = lines.stream()
            .map( line -> String.join(
                "|", line
            ))
            .collect(Collectors.toList());

        doc.append(newLines);
    }
}