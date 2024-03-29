package com.ss.lms.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.ss.lms.dao.*;
import com.ss.lms.entity.*;
import com.ss.lms.exceptions.DuplicateIdException;
import com.ss.lms.exceptions.EntityDoesNotExistException;

public class BookService {
	
	private AuthorDao authorDao;
	private BookDao bookDao;
	private PublisherDao publisherDao;
	
	
	public BookService(AuthorDao authorDao, BookDao bookDao, PublisherDao publisherDao) {
		this.authorDao = authorDao;
		this.bookDao = bookDao;
		this.publisherDao = publisherDao;
	}

	// Throws DuplicateIdException if the book exists
	// Throws EntityDoesNotExistException if the author or publisher
	//	does not exist
	public void insert(Book book) 
			throws DuplicateIdException, EntityDoesNotExistException, IOException {
		
		if(bookDao.get(book.getId()).isPresent()) {
			throw new DuplicateIdException();
		}
		
		validateBookRelations(book);
	
		bookDao.insert(book);
	}	
	
	public Optional<Book> get(int id) throws IOException {
		
		return bookDao.get(id);
	}
	
	public List<Book> getAll() throws IOException {
		return bookDao.getAll();
	}
	
	// Throws EntityDoesNotExistException if the author or publisher
	//	does not exist
	public void update(Book book) 
			throws EntityDoesNotExistException, IOException {
		
		validateBookRelations(book);
		
		bookDao.update(book);
	}
	
	public void delete(Book book) throws IOException {
		
		bookDao.delete(book);
	}
	
	// Throws EntityDoesNotExistException if the author or publisher
	//	does not exist
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
