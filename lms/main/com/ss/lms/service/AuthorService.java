package com.ss.lms.service;

import java.io.IOException;

import java.util.List;
import java.util.Optional;

import com.ss.lms.dao.*;
import com.ss.lms.entity.*;
import com.ss.lms.exceptions.DuplicateIdException;

public class AuthorService {

	private AuthorDao authorDao;
	private BookDao  bookDao;
	
	public AuthorService(AuthorDao authorDao, BookDao bookDao) {
		this.authorDao = authorDao;
		this.bookDao = bookDao;
	}
	
	//Throws DuplicateIdException if the author id exists
	public void insert(Author author) 
			throws DuplicateIdException, IOException {
		
		if(authorDao.get(author.getId()).isPresent()) {
			throw new DuplicateIdException();
		}
		
		authorDao.insert(author);
	}
	
	public Optional<Author> get(int id) throws IOException {
		return authorDao.get(id);
	}
	
	public List<Author> getAll() throws IOException {
		return authorDao.getAll();
	}
	
	public void update(Author author) throws IOException {
		
		authorDao.update(author);
	}
	
	//Also deletes any books by the author
	public void delete(Author author) throws IOException {
		
		bookDao.deleteAuthorBooks(author);
		authorDao.delete(author);
	}
}
