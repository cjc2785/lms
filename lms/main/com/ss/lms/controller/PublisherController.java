package com.ss.lms.controller;

import com.ss.lms.view.PublisherView;
import com.ss.lms.view.CategoryView;
import com.ss.lms.entity.Publisher;
import com.ss.lms.exceptions.DuplicateIdException;
import com.ss.lms.service.PublisherService;

import java.util.List;
import java.util.Optional;
import java.io.IOException;

public class PublisherController implements PublisherView.Delegate {
	
	private PublisherView view;
	private CategoryView categoryView;
	private PublisherService service;
	
	
	public PublisherController(PublisherView view, CategoryView categoryView, PublisherService service) {
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
				List<Publisher> publishers = service.getAll();
				view.showMany(publishers);
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
	public void onInsert(Publisher publisher) {
		try {
			service.insert(publisher);
			categoryView.showWithReturnMessage();
		} catch(DuplicateIdException e) {
			categoryView.showWithInsertDuplicateIdMessage();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public void onSelectForQuery(int id) {
		try {
			Optional<Publisher> optPublisher = service.get(id);
			view.showOne(optPublisher);
			categoryView.showWithReturnMessage();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onSelectForUpdate(int id) {
		try {
			Optional<Publisher> optPublisher = service.get(id);
			view.showUpdate(optPublisher);
			categoryView.showWithReturnMessage();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public void onUpdate(Publisher publisher) {
		try {
			service.update(publisher);
			categoryView.showWithReturnMessage();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public void onDelete(int id) {
		try {
			Optional<Publisher> optPublisher = service.get(id);
			if(optPublisher.isPresent()) {
				service.delete(optPublisher.get());
			}
			categoryView.showWithReturnMessage();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}
