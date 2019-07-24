package com.test.bookproject.entity;

import java.util.Date;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
	
@Entity
public class Member {
	
	
	
	@Id
	private String email;
	
	@Column
	private String password;
	
	@Column
	private String username;
	
	@Column
	private Date date = Calendar.getInstance().getTime();
	
	public Member() {
		super();
	}
	
	public Member(String email, String password, String username){ 
		super(); 
		this.email = email;
		this.password = password; 
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Member [email=" + email + ", password=" + password + ", username=" + username + ", date=" + date + "]";
	}

		
	

}
