package com.ss.lms.service;

public class EntityDoesNotExistException extends Exception {

	private final String entity;
	
	public static final long serialVersionUID = 1L;
	
	public EntityDoesNotExistException(String entity) {
		this.entity = entity;
	}
	
	public String getEntity() {
		return entity;
	}
}
