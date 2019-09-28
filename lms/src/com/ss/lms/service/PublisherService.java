package com.ss.lms.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.ss.lms.dao.PublisherDao;
import com .ss.lms.dao.BookDao;
import com.ss.lms.entity.Publisher;

public class PublisherService {
	
	private PublisherDao publisherDao;
	private BookDao bookDao;
	
	
	public void insert(Publisher publisher) throws IOException {
		
		publisherDao.insert(publisher);
	}
	
	public Optional<Publisher> get(int id) throws IOException {
		return publisherDao.get(id);
	}
	
	public List<Publisher> getAll() throws IOException {
		return publisherDao.getAll();
	}
	
	public void update(Publisher publisher) throws IOException {
		
		publisherDao.update(publisher);
	}
	
	public void delete(Publisher publisher) throws IOException {
		bookDao.deletePublisherBooks(publisher);
		publisherDao.delete(publisher);
	}
}