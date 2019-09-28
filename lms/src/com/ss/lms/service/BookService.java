package com.ss.lms.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.ss.lms.dao.*;
import com.ss.lms.entity.*;

public class BookService {
	
	private AuthorDao authorDao;
	private BookDao bookDao;
	private PublisherDao publisherDao;
	
	
	public void insert(Book book) 
			throws EntityDoesNotExistException, IOException {
			
		validateBookRelations(book);
	
		bookDao.insert(book);
	}	
	
	public Optional<Book> get(int id) throws IOException {
		
		return bookDao.get(id);
	}
	
	public List<Book> getAll() throws IOException {
		return bookDao.getAll();
	}
	
	public void update(Book book) 
			throws EntityDoesNotExistException, IOException {
		
		validateBookRelations(book);
		
		bookDao.update(book);
	}
	
	
	private void validateBookRelations(Book book) 
			throws EntityDoesNotExistException, IOException {
			

		Optional<Author> optAuthor = authorDao.get(book.getAuthor());
		
		if(optAuthor.isEmpty()) {
			throw new  EntityDoesNotExistException("author");
		}
		
		Optional<Publisher> optPublisher = publisherDao.get(book.getPublisher());
		
		if(optPublisher.isEmpty()) {
			throw new EntityDoesNotExistException("publisher");
		}
	}	
}
