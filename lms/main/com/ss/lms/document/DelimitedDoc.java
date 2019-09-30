package com.ss.lms.document;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;

/*
 * An object representing a file whose lines are 
 *  fields delimited by the pipe character.
 * 	The first field of any line of the file
 *  must be an integer id. 
 */

public class DelimitedDoc {

    private Document doc;
    private final String delimiter = "\\|";

    public DelimitedDoc(Document doc) {
        this.doc = doc;
    }

    // Returns the fields of the first line whose id field
    //  matches the supplied id or Optional.empty if 
    //  there is no match
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

    // The integer value of the first field in the supplied 
    //  list of fields (the id field) is all that will be matched. 
    //  Overwrites the matched line with the new field values
    public void updateLine(List<String> fields) throws IOException {

        String id = fields.get(0);

        List<List<String>> lines = getLines();

        List<List<String>> newLines = lines.stream()
            .map(line -> line.get(0).equals(id) ? fields : line)
            .collect(Collectors.toList());
        
        save(newLines);
    }

    // The integer value of the first field in the supplied 
    //  list of fields (the id field) is all that will be matched. 
    public void deleteLine(List<String> fields) throws IOException {

        String id = fields.get(0);
        List<List<String>> lines = getLines();

        List<List<String>> newLines = lines.stream()
            .filter(line -> !line.get(0).equals(id))
            .collect(Collectors.toList());

        
        save(newLines);
    }

    // Sets the lines of the file to the supplied lines. Each
    //  line should be a list of fields to be joined by the pipe
    //  character. The first field in each line should be an 
    //  integer id string that will be used to identify the line
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