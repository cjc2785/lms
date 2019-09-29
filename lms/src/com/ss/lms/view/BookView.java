package com.ss.lms.view;

import java.util.List;
import java.util.Optional;

import com.ss.lms.entity.Book;

public class BookView implements View {
	
	public interface Delegate {
		public void onActionSelect(int num);
		public void onInsert(Book book);
		public void onSelectForQuery(int id);
		public void onSelectForUpdate(int id);
		public void onUpdate(Book book);
		public void onDelete(int id);
	}
	
	private Delegate delegate = null;

	
	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}
	
	public void showSelectForQuery() {
		
		System.out.println("Enter a book id: ");
		int id = View.nextInt();
		
		delegate.onSelectForQuery(id);
	}
	
	public void showOne(Optional<Book> optBook) {
		
		String display = optBook.map(book -> "id: " + book.getId() + "\n" +
				"name: " + book.getName() + "\n" +
				"author id: " + book.getAuthor() + "\n" +
				"publisher id: " + book.getPublisher())
			.orElse("No book found");
		
		System.out.println(display);
	}
	
	public void showMany(List<Book> books) {
		for(Book book : books) {
			showOne(Optional.of(book));
			System.out.println();
		}
	}
	
	public void showSelectForUpdate() {
		
		String display = "Enter a book id: ";
		System.out.println(display);
		
		int id = View.nextInt();
		delegate.onSelectForUpdate(id);
	}
	
	public void showUpdate(Optional<Book> optBook) {
		
		if(optBook.isEmpty()) {
			System.out.println("No book with the given id exists");
		}
		else {
			showUpdate(optBook.get());
		}
	}
	
	public void showUpdate(Book book) {
		System.out.println("Enter a new name: ");
		String name = View.nextLine();
			
		System.out.println("Enter a new author id: ");
		int author = View.nextInt();
			
		System.out.println("Enter a new publisher id: ");
		int publisher = View.nextInt();
		delegate.onUpdate(new Book(book.getId(), name, author, publisher));	
	}
	
	
	public void showInsert() {
		
		System.out.println("Enter an id: ");
		int id = View.nextInt();
		
		System.out.println("Enter a name: ");
		String name = View.nextLine();
		
		System.out.println("Enter an author id: ");
		int author = View.nextInt();
		
		System.out.println("Enter a publisher id: ");
		int publisher = View.nextInt();
		
		delegate.onInsert(new Book(id, name, author, publisher));
	}
	
	public void showEntityDoesNotExist(String entityName) {
		System.out.println("No such " + entityName + " exists");
	}
	
	public void showDelete() {
		
		System.out.println("Enter a book id: ");
		int id = View.nextInt();
		
		delegate.onDelete(id);
	}
	
	
	
	public void show() {
		
		String display = "Choose an action number: \n" +
				 "1: Insert a book\n" + 
				 "2: Get a book\n" + 
				 "3: Get all books\n" +
				 "4: Update a book\n" +
				 "5: Delete a book\n";
		
		
		System.out.println(display);
		
		int num = View.nextInt();
		delegate.onActionSelect(num);
	}
}