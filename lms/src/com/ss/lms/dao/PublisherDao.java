package com.ss.lms.dao;

import java.io.IOException;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import com.ss.lms.document.DelimitedDoc;
import com.ss.lms.entity.Publisher;


public class PublisherDao {

    private DelimitedDoc doc;

    public PublisherDao(DelimitedDoc doc) {
        this.doc = doc;
    }

    private static Publisher fromFields(List<String> fields) {
        int id = Integer.parseInt(fields.get(0));
        String name = fields.get(1);
        String address = fields.get(2);
        return new Publisher(id, name, address);
    }

    private static List<String> toFields(Publisher Publisher) {

        return List.of(
        		Integer.toString(Publisher.getId()), 
        		Publisher.getName(),
        		Publisher.getAddress());
    }
    
    

    public void insert(Publisher publisher) throws IOException {

        List<String> fields = PublisherDao.toFields(publisher);
        doc.appendLine(fields);
    }

    public Optional<Publisher> get(int id) throws IOException {

        Optional<List<String>> fields = doc.getLine(id);
        return fields.map(PublisherDao::fromFields);
    }

    public List<Publisher> getAll() throws IOException {

        List<List<String>> publishersRaw = doc.getLines();

        List<Publisher> publishers = publishersRaw.stream()
            .map(PublisherDao::fromFields)
            .collect(Collectors.toList());

        return publishers;
    }

    public void update(Publisher publisher) throws IOException {

        List<String> fields = PublisherDao.toFields(publisher);
        doc.updateLine(fields);
    }

    public void delete(Publisher publisher) throws IOException {

        List<String> fields = PublisherDao.toFields(publisher);
        doc.deleteLine(fields);
    }
}