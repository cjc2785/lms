package com.ss.lms.controller;

import com.ss.lms.view.*;
import java.util.List;

public class CategoryController implements CategoryView.Delegate {
	
	public CategoryController(List<View> views) {
		this.views = views;
	}

	private List<View> views;
	
	@Override
	public void onCategorySelect(int num) {
		
		views.get(num).show();
	}
}
