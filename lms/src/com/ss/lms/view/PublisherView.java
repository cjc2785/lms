package com.ss.lms.view;

import java.util.List;
import java.util.Optional;

import com.ss.lms.entity.Publisher;

public class PublisherView implements View {
	
	public interface Delegate {
		public void onActionSelect(int num);
		public void onInsert(Publisher publisher);
		public void onSelectForQuery(int id);
		public void onSelectForUpdate(int id);
		public void onUpdate(Publisher publisher);
		public void onDelete(int id);
	}
	
	private Delegate delegate;
	
	public PublisherView(Delegate delegate) {
		this.delegate = delegate;
	}
	
	public void showSelectForQuery() {
		
		System.out.println("Enter a publisher id: ");
		int id = View.scanner.nextInt();
		
		delegate.onSelectForQuery(id);
	}
	
	public void showOne(Optional<Publisher> optPublisher) {
		
		String display = optPublisher.map(publisher -> "id: " + publisher.getId() + "\n" +
				"name: " + publisher.getName() + "\n" +
				"address: " + publisher.getAddress() + "\n")
			.orElse("No publisher found");
		
		System.out.println(display);
	}
	
	public void showMany(List<Publisher> publishers) {
		for(Publisher publisher : publishers) {
			showOne(Optional.of(publisher));
			System.out.println();
		}
	}
	
	public void showSelectForUpdate() {
		
		String display = "Enter a publisher id: ";
		System.out.println(display);
		
		int id = View.scanner.nextInt();
		delegate.onSelectForUpdate(id);
	}
	
	public void showUpdate(Optional<Publisher> optPublisher) {
		
		if(optPublisher.isEmpty()) {
			System.out.println("No publisher with the given id exists");
		}
		else {
			showUpdate(optPublisher.get());
		}
	}

	
	public void showUpdate(Publisher publisher) {
		
		System.out.println("Enter a new name: ");
		String name = View.scanner.nextLine();
		
		System.out.println("Enter a new address: ");
		String address = View.scanner.nextLine();
		
		
		delegate.onUpdate(new Publisher(publisher.getId(), name, address));
	}
	
	public void showInsert() {
		
		System.out.println("Enter an id: ");
		int id = View.scanner.nextInt();
		
		System.out.println("Enter a name: ");
		String name = View.scanner.nextLine();
		
		System.out.println("Enter an address: ");
		String address = View.scanner.nextLine();
		
		delegate.onInsert(new Publisher(id, name, address));
	}
	
	public void showDelete() {
		
		System.out.println("Enter an id: ");
		int id = View.scanner.nextInt();
		
		delegate.onDelete(id);
	}
	
	public void show() {
		
		String display = "Choose an action number: \n" +
				 "1: Insert a publisher\n" + 
				 "2: Get a publisher\n" + 
				 "3: Get all publishers\n" +
				 "4: Update a publisher\n" +
				 "5: Delete a publisher\n";
		
		
		System.out.println(display);
		
		int num = View.scanner.nextInt();
		delegate.onActionSelect(num);
	}
}