package com.test.bookproject.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

public class MyHistoryId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyHistoryId() {
		super();
		// TODO Auto-generated constructor stub
	}
		
	public MyHistoryId(String email, String keyword) {
		super();
		this.email = email;
		this.keyword = keyword;
	}

	@Column
	private String email;
	
	@Column
	private String keyword;

	public String getEmail() {
		return email;
	}

	public void setId(String email) {
		this.email = email;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	
	

}
