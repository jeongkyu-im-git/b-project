package com.test.bookproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class HitHistory {

	public HitHistory() {
		super();
	}

	public HitHistory(String keyword, long searchCount) {
		super();
		this.keyword = keyword;
		this.searchCount = searchCount;
	}

	@Id
	private String keyword;
	
	@Column
	private long searchCount;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public long getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(long searchCount) {
		this.searchCount = searchCount;
	}

	@Override
	public String toString() {
		return "HitHistory [keyword=" + keyword + ", searchCount=" + searchCount + "]";
	}
	
	
	
}
