package com.ss.lms.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ss.lms.dao.BookDao;
import com.ss.lms.dao.PublisherDao;
import com.ss.lms.document.DelimitedDoc;
import com.ss.lms.document.Directory;
import com.ss.lms.document.Document;
import com.ss.lms.entity.Publisher;
import com.ss.lms.service.PublisherService;
import com.ss.lms.service.DuplicateIdException;

public class PublisherServiceTest {

	
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
	void insertShouldAddNewPublisher() 
			throws IOException, DuplicateIdException {
		
		Document publisherFile = new Document("lmsdatatest/publisher.txt");
		Document bookFile = new Document("lmsdatatest/book.txt");

		
		BookDao bookDao = new BookDao(new DelimitedDoc(bookFile));
		PublisherDao publisherDao = new PublisherDao(new DelimitedDoc(publisherFile));
		
		PublisherService publisherService = new PublisherService(bookDao, publisherDao);
		
		publisherFile.append(List.of(
				"0|mike publishing|1 mike street",
				"1|john publishing|1 john street",
				"2|bob publishing|1 bob street"
				));
		
		Publisher newPublisher = new Publisher(5, "lisa publishing", "1 lisa street");
		
		publisherService.insert(newPublisher);
		Publisher actual = publisherService.get(5).get();
		
		
		assertEquals(5, actual.getId());
		assertEquals("lisa publishing", actual.getName());
	}
	
	
	@Test
	void insertShouldThrowWhenAddingDuplicateId() throws IOException {
		
		Document publisherFile = new Document("lmsdatatest/publisher.txt");
		Document bookFile = new Document("lmsdatatest/book.txt");

		
		BookDao bookDao = new BookDao(new DelimitedDoc(bookFile));
		PublisherDao publisherDao = new PublisherDao(new DelimitedDoc(publisherFile));
		
		PublisherService publisherService = new PublisherService(bookDao, publisherDao);
		
		publisherFile.append(List.of(
				"0|mike publishing|1 mike street",
				"1|john publishing|1 john street",
				"2|bob publishing|1 bob street"
				));
		
		Publisher newPublisher = new Publisher(1, "lisa publishing", "1 lisa street");
		
		
		assertThrows(DuplicateIdException.class, () -> {
			publisherService.insert(newPublisher);
		});
	}
	
	
	@Test
	void updateShouldSetPublisherNameAndAddress() throws IOException {
		
		Document publisherFile = new Document("lmsdatatest/publisher.txt");
		Document bookFile = new Document("lmsdatatest/book.txt");

		
		BookDao bookDao = new BookDao(new DelimitedDoc(bookFile));
		PublisherDao publisherDao = new PublisherDao(new DelimitedDoc(publisherFile));
		
		PublisherService publisherService = new PublisherService(bookDao, publisherDao);
		
		publisherFile.append(List.of(
				"0|mike publishing|1 mike street",
				"1|john publishing|1 john street",
				"2|bob publishing|1 bob street"
				));
		
		Publisher updatedPublisher = new Publisher(1, "new lisa publishing", "1 new lisa street");

		
		publisherService.update(updatedPublisher);
		
		Publisher actual = publisherService.get(1).get();
		
		assertEquals("new lisa publishing", actual.getName());
		assertEquals("1 new lisa street", actual.getAddress());
	}
	
	
	
	@Test
	void deleteShouldRemovePublisher() throws IOException {
		
		Document publisherFile = new Document("lmsdatatest/publisher.txt");
		Document bookFile = new Document("lmsdatatest/book.txt");

		
		BookDao bookDao = new BookDao(new DelimitedDoc(bookFile));
		PublisherDao publisherDao = new PublisherDao(new DelimitedDoc(publisherFile));
		
		PublisherService publisherService = new PublisherService(bookDao, publisherDao);
		
		publisherFile.append(List.of(
				"0|mike publishing|1 mike street",
				"1|john publishing|1 john street",
				"2|bob publishing|1 bob street"
				));
		
		Publisher publisher = new Publisher(
				1, "john publishing", "1 john street");
		
		publisherService.delete(publisher);
		
		boolean hasMike = publisherService.get(0).isPresent();
		boolean hasJohn = publisherService.get(1).isPresent();
		boolean hasBob = publisherService.get(2).isPresent();
		
		assertTrue(hasMike);
		assertFalse(hasJohn);
		assertTrue(hasBob);
	}
	
	
	
	@Test 
	void deleteShouldRemoveBooksByPublisher() throws IOException {
		
		Document publisherFile = new Document("lmsdatatest/publisher.txt");
		Document bookFile = new Document("lmsdatatest/book.txt");

		
		BookDao bookDao = new BookDao(new DelimitedDoc(bookFile));
		PublisherDao publisherDao = new PublisherDao(new DelimitedDoc(publisherFile));
		
		PublisherService publisherService = new PublisherService(bookDao, publisherDao);
		
		publisherFile.append(List.of(
				"0|mike publishing|1 mike street",
				"1|john publishing|1 john street",
				"2|bob publishing|1 bob street"
				));
		
		//book id|book name|book author|book publisher
		bookFile.append(List.of(
				"0|mike's book|0|0",
				"1|john's 1st book|0|1",
				"2|bob's book|1|2|",
				"3|john's 2nd book|0|1"
				));

		Publisher publisher = new Publisher(
				1, "john publishing", "1 john street");
		
		publisherService.delete(publisher);
		
		boolean hasMikesBook = publisherService.get(0).isPresent();
		boolean hasJohns1stBook = publisherService.get(1).isPresent();
		boolean hasBobsBook = publisherService.get(2).isPresent();
		boolean hasJohns2ndBook = publisherService.get(3).isPresent();
			
		
		assertTrue(hasMikesBook);
		assertFalse(hasJohns1stBook);
		assertTrue(hasBobsBook);
		assertFalse(hasJohns2ndBook);
	}
}
