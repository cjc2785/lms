package com.ss.lms.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.ss.lms.dao.*;
import com.ss.lms.entity.*;

public class AuthorService {
	
	private AuthorDao authorDao;
	private BookDao  bookDao;
	
	
	public void insert(Author author) throws IOException {
		
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
	
	public void delete(Author author) throws IOException {
		
		bookDao.deleteAuthorBooks(author);
		authorDao.delete(author);
	}
}
