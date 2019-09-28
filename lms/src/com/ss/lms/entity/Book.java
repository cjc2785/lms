package com.ss.lms.entity;

public class Book {
	
	private int id;
	private String name;
	private int author;
	private int publisher;
	
	public Book(int id, String name, int author, int publisher) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public int getPublisher() {
		return publisher;
	}

	public void setPublisher(int publisher) {
		this.publisher = publisher;
	}
}
