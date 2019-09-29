package com.ss.lms.controller;

import com.ss.lms.view.BookView;
import com.ss.lms.view.CategoryView;
import com.ss.lms.entity.Book;
import com.ss.lms.service.BookService;
import com.ss.lms.service.DuplicateIdException;
import com.ss.lms.service.EntityDoesNotExistException;

import java.util.List;
import java.util.Optional;
import java.io.IOException;

public class BookController implements BookView.Delegate {
	
	private BookView view;
	private CategoryView categoryView;
	private BookService service;
	
	
	public BookController(BookView view, CategoryView categoryView, BookService service) {
		this.view = view;
		this.categoryView = categoryView;
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
				categoryView.showWithReturnMessage();
			} catch(IOException e) {
				throw new RuntimeException(e);
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
			categoryView.showWithReturnMessage();
		} catch(DuplicateIdException e) {
			categoryView.showWithInsertDuplicateIdMessage();
		} catch(EntityDoesNotExistException e) {
			categoryView.showWithEntityDoesNotExist(
					"insert", e.getEntity());
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public void onSelectForQuery(int id) {
		try {
			Optional<Book> optBook = service.get(id);
			view.showOne(optBook);
			categoryView.showWithReturnMessage();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onSelectForUpdate(int id) {
		try {
			Optional<Book> optBook = service.get(id);
			view.showUpdate(optBook);
			categoryView.showWithReturnMessage();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public void onUpdate(Book book) {
		try {
			service.update(book);
			categoryView.showWithReturnMessage();
		} catch(EntityDoesNotExistException e) {
			categoryView.showWithEntityDoesNotExist(
					"update", e.getEntity());
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public void onDelete(int id) {
		try {
			Optional<Book> optBook = service.get(id);
			if(optBook.isPresent()) {
				service.delete(optBook.get());
			}
			categoryView.showWithReturnMessage();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}
