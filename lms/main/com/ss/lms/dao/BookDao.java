package com.ss.lms.dao;

import java.io.IOException;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import com.ss.lms.document.DelimitedDoc;
import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.Publisher;


public class BookDao {

    private DelimitedDoc doc;

    public BookDao(DelimitedDoc doc) {
        this.doc = doc;
    }
    
    // field 0: book id integer string
    // field 1: book name
    // field 2: book author id integer string
    // field 3: book publisher id integer string
    private static Book fromFields(List<String> fields) {
        int id = Integer.parseInt(fields.get(0));
        String name = fields.get(1);
        int author = Integer.parseInt(fields.get(2));
        int publisher = Integer.parseInt(fields.get(3));
        return new Book(id, name, author, publisher);
    }

    //Convert the book into string fields to be
    // joined by the pipe character
    private static List<String> toFields(Book book) {

        return List.of(
        		Integer.toString(book.getId()), 
        		book.getName(),
        		Integer.toString(book.getAuthor()),
        		Integer.toString(book.getPublisher()));
    }
    
    

    public void insert(Book book) throws IOException {

        List<String> fields = BookDao.toFields(book);
        doc.appendLine(fields);
    }

    public Optional<Book> get(int id) throws IOException {

        Optional<List<String>> fields = doc.getLine(id);
        return fields.map(BookDao::fromFields);
    }

    public List<Book> getAll() throws IOException {

        List<List<String>> booksRaw = doc.getLines();

        List<Book> books = booksRaw.stream()
            .map(BookDao::fromFields)
            .collect(Collectors.toList());

        return books;
    }
    
	public List<Book> getAuthorBooks(Author author) throws IOException {
		
		List<Book> books = getAll();
		
		return books.stream()
			.filter(book -> book.getAuthor() == author.getId())
			.collect(Collectors.toList());
	}
	
	public List<Book> getPublisherBooks(Publisher publisher) throws IOException {
		
		List<Book> books = getAll();
		
		return books.stream()
			.filter(book -> book.getPublisher() == publisher.getId())
			.collect(Collectors.toList());
	}
	
	public void deletePublisherBooks(Publisher publisher) throws IOException {
		
		deleteBooks(getPublisherBooks(publisher));
	}
	
	public void deleteAuthorBooks(Author author) throws IOException {
		
		deleteBooks(getAuthorBooks(author));
	}


    public void update(Book book) throws IOException {

        List<String> fields = BookDao.toFields(book);
        doc.updateLine(fields);
    }

    // Only depends on the id of the supplied book
    public void delete(Book book) throws IOException {

        List<String> fields = BookDao.toFields(book);
        doc.deleteLine(fields);
    }
    
    public void deleteBooks(List<Book> books) throws IOException {
    	
    	for(Book book : books) {
    		delete(book);
    	}
    }
}