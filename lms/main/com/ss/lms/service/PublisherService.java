package com.ss.lms.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.ss.lms.dao.PublisherDao;
import com .ss.lms.dao.BookDao;
import com.ss.lms.entity.Publisher;
import com.ss.lms.exceptions.DuplicateIdException;

public class PublisherService {
	
	private BookDao bookDao;
	private PublisherDao publisherDao;
	

	public PublisherService(BookDao bookDao, PublisherDao publisherDao) {
		this.bookDao = bookDao;
		this.publisherDao = publisherDao;
	}
	
	//Throws DuplicateIdException if the publisher exists
	public void insert(Publisher publisher) 
			throws DuplicateIdException, IOException {
		
		if(publisherDao.get(publisher.getId()).isPresent()) {
			throw new DuplicateIdException();
		}
		
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
	
	//Also deletes any books by the publisher
	public void delete(Publisher publisher) throws IOException {
		bookDao.deletePublisherBooks(publisher);
		publisherDao.delete(publisher);
	}
}