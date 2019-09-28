package com.ss.lms.view;

import java.util.List;
import java.util.Optional;

import com.ss.lms.entity.Author;

public class AuthorView implements View {
	
	public interface Delegate {
		public void onActionSelect(int num);
		public void onInsert(Author author);
		public void onSelectForQuery(int id);
		public void onSelectForUpdate(int id);
		public void onUpdate(Author author);
		public void onDelete(int id);
	}
	
	private Delegate delegate;
	
	public AuthorView(Delegate delegate) {
		this.delegate = delegate;
	}
	
	public void showSelectForQuery() {
		
		System.out.println("Enter an author id: ");
		int id = View.scanner.nextInt();
		
		delegate.onSelectForQuery(id);
	}
	
	public void showOne(Optional<Author> optAuthor) {
		
		String display = optAuthor.map(author -> "id: " + author.getId() + "\n" +
				"name: " + author.getName())
			.orElse("No author found");
		
		System.out.println(display);
	}
	
	public void showMany(List<Author> authors) {
		for(Author author : authors) {
			showOne(Optional.of(author));
			System.out.println();
		}
	}
	
	public void showSelectForUpdate() {
		
		String display = "Enter an author id: ";
		System.out.println(display);
		
		int id = View.scanner.nextInt();
		delegate.onSelectForUpdate(id);
	}
	
	
	public void showUpdate(Optional<Author> optAuthor) {
		
		if(optAuthor.isEmpty()) {
			System.out.println("No author with the given id exists");
		}
		else {
			showUpdate(optAuthor.get());
		}
	}
	
	public void showUpdate(Author author) {
		
		System.out.println("Enter a new name: ");
		
		String name = View.scanner.nextLine();
		
		delegate.onUpdate(new Author(author.getId(), name));
	}
	
	public void showInsert() {
		
		System.out.println("Enter an id: ");
		int id = View.scanner.nextInt();
		
		System.out.println("Enter a name: ");
		String name = View.scanner.nextLine();
		
		delegate.onInsert(new Author(id, name));
	}
	
	public void showDelete() {
		
		String display = "Enter an author id: ";
		System.out.println(display);
		
		int id = View.scanner.nextInt();
		delegate.onDelete(id);
	}

	public void show() {
		
		String display = "Choose an action number: \n" +
				 "1: Insert an author\n" + 
				 "2: Get an author\n" + 
				 "3: Get all authors\n" +
				 "4: Update an author\n" +
				 "5: Delete an author\n";
		
		
		System.out.println(display);
		
		int num = View.scanner.nextInt();
		delegate.onActionSelect(num);
	}
}