package com.ss.lms.dao;

import java.io.IOException;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import com.ss.lms.document.DelimitedDoc;
import com.ss.lms.entity.Author;


public class AuthorDao {

    private DelimitedDoc doc;

    public AuthorDao(DelimitedDoc authDoc) {
        doc = authDoc;
    }

    // field 0: author id integer string
    // field 1: author name
    private static Author fromFields(List<String> fields) {
        int id = Integer.parseInt(fields.get(0));
        String name = fields.get(1);
        return new Author(id, name);
    }

    //Convert the author into string fields to be
    // joined by the pipe character
    private static List<String> toFields(Author author) {

        String idField = Integer.toString(author.getId());
        String nameField = author.getName();

        return List.of(idField, nameField);
    }

    public void insert(Author author) throws IOException {

        List<String> fields = AuthorDao.toFields(author);
        doc.appendLine(fields);
    }

    public Optional<Author> get(int id) throws IOException {

        Optional<List<String>> fields = doc.getLine(id);
        return fields.map(AuthorDao::fromFields);
    }

    public List<Author> getAll() throws IOException {

        List<List<String>> authorsRaw = doc.getLines();

        List<Author> authors = authorsRaw.stream()
            .map(AuthorDao::fromFields)
            .collect(Collectors.toList());

        return authors;
    }

    public void update(Author author) throws IOException {

        List<String> fields = AuthorDao.toFields(author);
        doc.updateLine(fields);
    }

    //Only depends on the id of the supplied author
    public void delete(Author author) throws IOException {

        List<String> fields = AuthorDao.toFields(author);
        doc.deleteLine(fields);
    }
}