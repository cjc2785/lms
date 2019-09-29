package com.ss.lms;

import java.io.IOException;

import com.ss.lms.document.*;
import com.ss.lms.dao.*;
import com.ss.lms.service.*;
import com.ss.lms.view.*;
import com.ss.lms.controller.*;

public class App {
	
	public static void main(String[] args) throws IOException {
	
		Directory dataFolder = new Directory("lmsdata");
		Document authorFile = new Document("lmsdata/author.txt");
		Document bookFile = new Document("lmsdata/book.txt");
		Document publisherFile = new Document("lmsdata/publisher.txt");
		
		dataFolder.createIfNotExists();
		authorFile.createIfNotExists();
		bookFile.createIfNotExists();
		publisherFile.createIfNotExists();
		
		AuthorDao authorDao = new AuthorDao(new DelimitedDoc(authorFile));
		BookDao bookDao = new BookDao(new DelimitedDoc(bookFile));
		PublisherDao publisherDao = new PublisherDao(new DelimitedDoc(publisherFile));
		
		AuthorService authorService = new AuthorService(authorDao, bookDao);
		BookService bookService = new BookService(authorDao, bookDao, publisherDao);
		PublisherService publisherService = new PublisherService(bookDao, publisherDao);
		
		AuthorView authorView = new AuthorView();
		BookView bookView = new BookView();
		PublisherView publisherView = new PublisherView();
		CategoryView categoryView = new CategoryView();
		
		AuthorController authorController = new AuthorController(
				authorView, categoryView, authorService);
		BookController bookController = new BookController(
				bookView, categoryView, bookService);
		PublisherController publisherController = new PublisherController(
				publisherView, categoryView, publisherService);
		CategoryController categoryController = new CategoryController(
				authorView, bookView, publisherView);
		
		authorView.setDelegate(authorController);
		bookView.setDelegate(bookController);
		publisherView.setDelegate(publisherController);
		categoryView.setDelegate(categoryController);
		
		categoryView.show();
	}
}
