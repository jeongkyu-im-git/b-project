package com.test.bookproject.service;

import java.util.List;

import com.test.bookproject.entity.HitHistory;
import com.test.bookproject.entity.MyHistory;
import com.test.bookproject.entity.Search;

public interface SearchService {

	public String query(Search search, String email);
	
	public List<MyHistory> getHistory();
	
	public List<HitHistory> getHitHistory();
}
