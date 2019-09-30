package com.ss.lms.view;

public class CategoryView implements View {
	
	private Delegate delegate;

	public interface Delegate {
		public void onCategorySelect(int num);
	}
	
	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}
	
	
	public void show() {
		String display = "Categories:\n" +
	            "1: Authors\n" +
	            "2: Books\n" + 
	            "3: Publishers\n" +
	            "\n" +
	            "Enter a category number: ";
		
		System.out.println(display);
		
		int num = View.nextInt();
		delegate.onCategorySelect(num);
	}
	
	// Called after a successful action to prompt the 
	//  user to restart the app
	public void showWithReturnMessage() {
		System.out.println("-----------------------------------------------------");
		System.out.println("Action complete, press enter to return to categories");
		
		View.nextLine();
		
		show();
	}
	
	// Called after an attempt to insert or update 
	//  an entity referencing an entity that does 
	//  not exist. Prompts the user to restart the app
	public void showWithEntityDoesNotExist(String action, String entityName) {
		System.out.println("Unable to " + action + ": No such " + entityName + " exists\n" + 
				"Press enter to return to categories");
		
		View.nextLine();
		
		show();
	}
	
	// Called after an attempt to insert an entity whose
	//  id already exists. Prompts the user to 
	//  restart the app
	public void showWithInsertDuplicateIdMessage() {
		
		System.out.println("Unable to insert: IDs must be unique.\n" + 
				"Press enter to return to categories");
		
		View.nextLine();
		
		show();
	}
}
