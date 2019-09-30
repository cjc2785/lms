package com.ss.lms.entity;

public class Author {

    private int id;
    private String name;

    public Author(int id, String name) {
    	this.id = id;
    	this.name = name;
    }   

    public int getId() {
        return id;
    }

    public void setId(int newID) {
        id = newID;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }
}