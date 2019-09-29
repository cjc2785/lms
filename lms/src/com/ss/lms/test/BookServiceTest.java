package com.ss.lms.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ss.lms.dao.AuthorDao;
import com.ss.lms.dao.BookDao;
import com.ss.lms.dao.PublisherDao;
import com.ss.lms.document.DelimitedDoc;
import com.ss.lms.document.Directory;
import com.ss.lms.document.Document;
import com.ss.lms.entity.Book;
import com.ss.lms.service.BookService;
import com.ss.lms.service.DuplicateIdException;
import com.ss.lms.service.EntityDoesNotExistException;

public class BookServiceTest {

	private Directory dataFolder = new Directory("lmsdatatest");
	private Document authorFile = new Document("lmsdatatest/author.txt");
	private Document bookFile = new Document("lmsdatatest/book.txt");
	private Document publisherFile = new Document("lmsdatatest/publisher.txt");
	

	private AuthorDao authorDao = new AuthorDao(new DelimitedDoc(authorFile));
	private BookDao bookDao = new BookDao(new DelimitedDoc(bookFile));
	private PublisherDao publisherDao = new PublisherDao(new DelimitedDoc(publisherFile));
	
	
	@BeforeEach
	public void setUp() throws IOException {
	
		dataFolder.createIfNotExists();
		authorFile.truncate();
		bookFile.truncate();
		publisherFile.truncate();
		
		authorFile.append(List.of(
				"0|mike davis",
				"1|john allen",
				"2|bob brown"
				));
		
		//book id|book name|book author|book publisher
		bookFile.append(List.of(
				"0|sports|0|1",
				"1|coding|1|0",
				"2|atlas|1|2"
				));
		
		publisherFile.append(List.of(
				"0|mike publishing|1 mike street",
				"1|john publishing|1 john street",
				"2|bob publishing|1 bob street"
				));
	}
	
	@Test
	void insertShouldAddNewBook() 
			throws IOException, EntityDoesNotExistException, DuplicateIdException {
		
		BookService bookService = new BookService(authorDao, bookDao, publisherDao);
		
		Book newBook = new Book(5, "world", 2, 0);
		
		bookService.insert(newBook);
		Book actual = bookService.get(5).get();
		
		
		assertEquals(5, actual.getId());
		assertEquals("world", actual.getName());
		assertEquals(2, actual.getAuthor());
		assertEquals(0, actual.getPublisher());
	}
	
	
	@Test
	void insertShouldThrowWhenAddingDuplicateId() 
			throws IOException, EntityDoesNotExistException, DuplicateIdException {
		
	
		BookService bookService = new BookService(authorDao, bookDao, publisherDao);
		
		
		Book newBook = new Book(1, "world", 2, 0);
		
		assertThrows(DuplicateIdException.class, () -> {
			bookService.insert(newBook);
		});
	}
	
	@Test
	void insertShouldThrowWhenAuthorDoesNotExist() 
			throws IOException, EntityDoesNotExistException, DuplicateIdException {
		

		BookService bookService = new BookService(authorDao, bookDao, publisherDao);

		Book newBook = new Book(3, "world", 0, 5);
		
		assertThrows(EntityDoesNotExistException.class, () -> {
			bookService.insert(newBook);
		});
	}
	
	@Test
	void insertShouldThrowWhenPublisherDoesNotExist() 
			throws IOException, EntityDoesNotExistException, DuplicateIdException {

		BookService bookService = new BookService(authorDao, bookDao, publisherDao);
		
		Book newBook = new Book(3, "world", 5, 0);
		
		assertThrows(EntityDoesNotExistException.class, () -> {
			bookService.insert(newBook);
		});
	}
	
	
	
	@Test
	void updateShouldSetBookFields() 
			throws IOException, EntityDoesNotExistException {

		
		BookService bookService = new BookService(authorDao, bookDao, publisherDao);

		Book updatedBook = new Book(1, "new world", 1, 2);
		
		bookService.update(updatedBook);
		
		Book actual = bookService.get(1).get();
		
		assertEquals("new world", actual.getName());
		assertEquals(1, actual.getAuthor());
		assertEquals(2, actual.getPublisher());
	}
	
	
	@Test
	void updateShouldThrowWhenAuthorDoesNotExist() 
			throws IOException, EntityDoesNotExistException {

		BookService bookService = new BookService(authorDao, bookDao, publisherDao);
		
		Book book = new Book(3, "new world", 5, 0);
		
		assertThrows(EntityDoesNotExistException.class, () -> {
			bookService.update(book);
		});
	}
	
	
	@Test
	void updateShouldThrowWhenPublisherDoesNotExist() 
			throws IOException, EntityDoesNotExistException {
		

		BookService bookService = new BookService(authorDao, bookDao, publisherDao);
		
		
		Book book = new Book(3, "new world", 0, 5);
		
		assertThrows(EntityDoesNotExistException.class, () -> {
			bookService.update(book);
		});
	}
	
	
	@Test
	void deleteShouldRemoveBook() throws IOException {

		BookService bookService = new BookService(authorDao, bookDao, publisherDao);

		
		Book book = new Book(1, "coding", 1, 0);
		
		bookService.delete(book);
		
		boolean hasSports = bookService.get(0).isPresent();
		boolean hasCoding = bookService.get(1).isPresent();
		boolean hasAtlas = bookService.get(2).isPresent();
		
		assertTrue(hasSports);
		assertFalse(hasCoding);
		assertTrue(hasAtlas);
	}
}
