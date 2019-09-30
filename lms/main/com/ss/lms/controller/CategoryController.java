package com.ss.lms.controller;

import com.ss.lms.view.*;

public class CategoryController implements CategoryView.Delegate {
	
	private AuthorView authorView;
	private BookView bookView;
	private PublisherView publisherView;
	
	
	public CategoryController(AuthorView authorView, BookView bookView, PublisherView publisherView) {
		this.authorView = authorView;
		this.bookView = bookView;
		this.publisherView = publisherView;
	}


	@Override
	public void onCategorySelect(int num) {
		
		getViews()[num - 1].show();
	}
	
	
	private View[] getViews() {
		
		View[] views = {authorView, bookView, publisherView};
		return views;
	}
}
