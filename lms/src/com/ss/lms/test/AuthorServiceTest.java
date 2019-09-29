package com.ss.lms.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ss.lms.dao.AuthorDao;
import com.ss.lms.dao.BookDao;
import com.ss.lms.document.DelimitedDoc;
import com.ss.lms.document.Directory;
import com.ss.lms.document.Document;
import com.ss.lms.entity.Author;
import com.ss.lms.service.AuthorService;
import com.ss.lms.service.DuplicateIdException;

class AuthorServiceTest {

	
	@BeforeEach
	public void setUp() throws IOException {

		Directory dataFolder = new Directory("lmsdatatest");
		Document authorFile = new Document("lmsdatatest/author.txt");
		Document bookFile = new Document("lmsdatatest/book.txt");
		Document publisherFile = new Document("lmsdatatest/publisher.txt");
	
		dataFolder.createIfNotExists();
		authorFile.truncate();
		bookFile.truncate();
		publisherFile.truncate();
	}
	
	@Test
	void insertAddsNewAuthor() 
			throws IOException, DuplicateIdException {
		
		Document authorFile = new Document("lmsdatatest/author.txt");
		Document bookFile = new Document("lmsdatatest/book.txt");
		
		AuthorDao authorDao = new AuthorDao(new DelimitedDoc(authorFile));
		BookDao bookDao = new BookDao(new DelimitedDoc(bookFile));
		
		AuthorService authorService = new AuthorService(authorDao, bookDao);
		
		authorFile.append(List.of(
				"0|mike davis",
				"1|john allen",
				"2|bob brown"
				));
		
		Author newAuthor = new Author(5, "lisa c");
		
		authorService.insert(newAuthor);
		Author actual = authorService.get(5).get();
		
		
		assertEquals(5, actual.getId());
		assertEquals("lisa c", actual.getName());
	}
	
	
	@Test
	void insertThrowsWhenAddingDuplicateId() throws IOException {
		
		Document authorFile = new Document("lmsdatatest/author.txt");
		Document bookFile = new Document("lmsdatatest/book.txt");
		
		AuthorDao authorDao = new AuthorDao(new DelimitedDoc(authorFile));
		BookDao bookDao = new BookDao(new DelimitedDoc(bookFile));
		
		AuthorService authorService = new AuthorService(authorDao, bookDao);
		
		
		authorFile.append(List.of(
				"0|mike davis",
				"1|john allen",
				"2|bob brown"
				));
		
		Author newAuthor = new Author(1, "lisa c");
		
		assertThrows(DuplicateIdException.class, () -> {
			authorService.insert(newAuthor);
		});
	}
	
	@Test
	void updateSetsAuthorName() throws IOException {
		
		Document authorFile = new Document("lmsdatatest/author.txt");
		Document bookFile = new Document("lmsdatatest/book.txt");
		
		AuthorDao authorDao = new AuthorDao(new DelimitedDoc(authorFile));
		BookDao bookDao = new BookDao(new DelimitedDoc(bookFile));
		
		AuthorService authorService = new AuthorService(authorDao, bookDao);
		
		
		authorFile.append(List.of(
				"0|mike davis",
				"1|john allen",
				"2|bob brown"
				));
		
		Author updatedAuthor = new Author(1, "john c");
		
		authorService.update(updatedAuthor);
		
		Author actual = authorService.get(1).get();
		
		assertEquals("john c", actual.getName());
	}
	
	@Test
	void deleteRemovesAuthor() throws IOException {
		
		Document authorFile = new Document("lmsdatatest/author.txt");
		Document bookFile = new Document("lmsdatatest/book.txt");
		
		AuthorDao authorDao = new AuthorDao(new DelimitedDoc(authorFile));
		BookDao bookDao = new BookDao(new DelimitedDoc(bookFile));
		
		AuthorService authorService = new AuthorService(authorDao, bookDao);
		
		
		authorFile.append(List.of(
				"0|mike davis",
				"1|john allen",
				"2|bob brown"
				));
		
		Author author = new Author(1, "john allen");
		
		authorService.delete(author);
		
		boolean hasMike = authorService.get(0).isPresent();
		boolean hasJohn = authorService.get(1).isPresent();
		boolean hasBob = authorService.get(2).isPresent();
		
		assertTrue(hasMike);
		assertFalse(hasJohn);
		assertTrue(hasBob);
	}
	
	@Test 
	void deleteRemovesBooksByAuthor() throws IOException {
		
		Document authorFile = new Document("lmsdatatest/author.txt");
		Document bookFile = new Document("lmsdatatest/book.txt");
		
		AuthorDao authorDao = new AuthorDao(new DelimitedDoc(authorFile));
		BookDao bookDao = new BookDao(new DelimitedDoc(bookFile));
		
		AuthorService authorService = new AuthorService(authorDao, bookDao);
		
		
		authorFile.append(List.of(
				"0|mike davis",
				"1|john allen",
				"2|bob brown"
				));
		
		//book id|book name|book author|book publisher
		bookFile.append(List.of(
				"0|mike's book|0|0",
				"1|john's 1st book|1|0",
				"2|bob's book|2|0",
				"3|john's 2nd book|1|0"
				));

		Author author = new Author(1, "john allen");
		authorService.delete(author);
		
		boolean hasMikesBook = authorService.get(0).isPresent();
		boolean hasJohns1stBook = authorService.get(1).isPresent();
		boolean hasBobsBook = authorService.get(2).isPresent();
		boolean hasJohns2ndBook = authorService.get(3).isPresent();
			
		
		assertTrue(hasMikesBook);
		assertFalse(hasJohns1stBook);
		assertTrue(hasBobsBook);
		assertFalse(hasJohns2ndBook);
	}
}
