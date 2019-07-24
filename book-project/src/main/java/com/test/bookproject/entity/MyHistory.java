package com.test.bookproject.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;

@IdClass(MyHistoryId.class)
@Entity
public class MyHistory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyHistory() {
		super();
		// TODO Auto-generated constructor stub
	}
		
	public MyHistory(String email, String keyword) {
		super();
		this.email = email;
		this.keyword = keyword;
	}

	@Id
	private String email;
	
	@Id
	private String keyword;
	
	@Column
	private int searchCount;
	
	@Column
	private Date date = Calendar.getInstance().getTime();

	public String getId() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "History [keyword=" + keyword + ", searchCount=" + searchCount + ", date=" + date + "]";
	}
	
	

}
