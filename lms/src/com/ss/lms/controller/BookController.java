package com.ss.lms.controller;

import com.ss.lms.view.BookView;
import com.ss.lms.entity.Book;
import com.ss.lms.service.BookService;
import com.ss.lms.service.EntityDoesNotExistException;

import java.util.List;
import java.util.Optional;
import java.io.IOException;

public class BookController implements BookView.Delegate {
	
	private BookView view;
	private BookService service;
	
	
	public BookController(BookView view, BookService service) {
		this.view = view;
		this.service = service;
	}
	
	@Override
	public void onActionSelect(int num) {
		switch(num) {
		case 1:
			view.showInsert();
			break;
		case 2:
			view.showSelectForQuery();
			break;
		case 3:
			try {
				List<Book> books = service.getAll();
				view.showMany(books);
			} catch(IOException e) {
				
			}
			break;
		case 4:
			view.showSelectForUpdate();
			break;
		case 5:
			view.showDelete();
		}
	}
	@Override
	public void onInsert(Book book) {
		try {
			service.insert(book);
		} catch(EntityDoesNotExistException e) {
			view.showEntityDoesNotExist(e.getEntity());
		} catch(IOException e) {
			
		}
	}
	@Override
	public void onSelectForQuery(int id) {
		try {
			Optional<Book> optBook = service.get(id);
			view.showOne(optBook);
		} catch(IOException e) {
			
		}
	}

	@Override
	public void onSelectForUpdate(int id) {
		try {
			Optional<Book> optBook = service.get(id);
			view.showUpdate(optBook);
		} catch(IOException e) {
			
		}
	}
	@Override
	public void onUpdate(Book book) {
		try {
			service.update(book);
		} catch(EntityDoesNotExistException e) {
			view.showEntityDoesNotExist(e.getEntity());
		} catch(IOException e) {
			
		}
	}
	@Override
	public void onDelete(int id) {
		try {
			Optional<Book> optBook = service.get(id);
			if(optBook.isPresent()) {
				service.delete(optBook.get());
			}
		} catch(IOException e) {
			
		}
	}
}
