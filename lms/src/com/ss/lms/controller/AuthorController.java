package com.ss.lms.controller;

import com.ss.lms.view.AuthorView;
import com.ss.lms.view.CategoryView;
import com.ss.lms.entity.Author;
import com.ss.lms.service.AuthorService;
import com.ss.lms.service.DuplicateIdException;

import java.util.List;
import java.util.Optional;
import java.io.IOException;

public class AuthorController implements AuthorView.Delegate {
	

	private AuthorView view;
	private CategoryView categoryView;
	private AuthorService service;
	
	
	public AuthorController(AuthorView view, CategoryView categoryView, AuthorService service) {
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
				List<Author> authors = service.getAll();
				view.showMany(authors);
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
			break;
		}
	}
	@Override
	public void onInsert(Author author) {
		try {
			service.insert(author);
			categoryView.showWithReturnMessage();
		} catch(DuplicateIdException e) {
			categoryView.showWithInsertDuplicateIdMessage();
			return;
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public void onSelectForQuery(int id) {
		try {
			Optional<Author> optAuthor = service.get(id);
			view.showOne(optAuthor);
			categoryView.showWithReturnMessage();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onSelectForUpdate(int id) {
		try {
			Optional<Author> optAuthor = service.get(id);
			view.showUpdate(optAuthor);
			categoryView.showWithReturnMessage();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public void onUpdate(Author author) {
		try {
			service.update(author);
			categoryView.showWithReturnMessage();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public void onDelete(int id) {
		try {
			Optional<Author> optAuthor = service.get(id);
			if(optAuthor.isPresent()) {
				service.delete(optAuthor.get());
			}
			categoryView.showWithReturnMessage();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}
