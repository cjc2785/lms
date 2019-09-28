package com.ss.lms.view;

public class CategoryView implements View {
	
	private Delegate delegate;
	
	
	public CategoryView(Delegate delegate) {
		this.delegate = delegate;
	}


	public interface Delegate {
		public void onCategorySelect(int num);
	}
	
	
	public void show() {
		String display = "Categories:\n" +
	            "1: Authors\n" +
	            "2: Books\n" + 
	            "3: Publishers\n" +
	            "\n" +
	            "Enter a category number: ";
		
		System.out.println(display);
		
		int num = View.scanner.nextInt();
		delegate.onCategorySelect(num);
	}
}
