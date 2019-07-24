package com.test.bookproject.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.test.bookproject.entity.HitHistory;
import com.test.bookproject.entity.MyHistory;
import com.test.bookproject.entity.Search;
import com.test.bookproject.repository.HitHistoryRepository;
import com.test.bookproject.repository.MyHistoryRepository;
import com.test.bookproject.service.SearchService;

@Service("searchService")
public class SearchServiceImpl implements SearchService {

	@Autowired
	MyHistoryRepository myHisotoryRepository;
	
	@Autowired
	HitHistoryRepository hitHisotoryRepository;

	@Override
	public String query(Search search, String email) {
		
		String result = "";
		
		try {
			
			try {
				result = getKakaoBookSearch(search);
				//result = getNaverBookSearch(search);
			} catch (Exception e) {
				// 에러 발생시 NAVER 로 자동 전환.
				result = getNaverBookSearch(search);
			}
			
			// 처음조회인 경우만..
			if (search.getPage() == 1) {
				// 검색히스토리 저장
				myHisotoryRepository.save(new MyHistory(email,search.getQuery()));
				
				// 조회 검색어 저장
				HitHistory history = hitHisotoryRepository.findOne(search.getQuery());
				if ( history == null ) {
					history = new HitHistory();
					history.setKeyword(search.getQuery());
					history.setSearchCount(1);
					hitHisotoryRepository.save(history);
				} else {					
					history.setSearchCount(history.getSearchCount()+1); 
					hitHisotoryRepository.save(history);
				}
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} 

		return result;
	}
	
	private String getKakaoBookSearch(Search search) throws Exception {
		
		String result = "";
		
		BufferedReader br = null;
		try {
			
			StringBuffer urlStr = new StringBuffer();
			urlStr.append("https://dapi.kakao.com/v3/search/book");
			urlStr.append("?query=").append(URLEncoder.encode(search.getQuery(), "UTF-8"));
			urlStr.append("&page=").append(search.getPage());
			urlStr.append("&size=").append(search.getSize());
			
			System.out.println(urlStr.toString());
			
			URL url = new URL(urlStr.toString());
			
			HttpsURLConnection urlconnection = (HttpsURLConnection) url.openConnection();
			urlconnection.setRequestMethod("GET");
			urlconnection.setRequestProperty("Authorization", "KakaoAK b877bfc3abf01c8b89f987d7ceda5b95");
			
			br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
			
			String line;
			while ( (line = br.readLine()) != null) {
				result += line + "\n";
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		return result;
		
	}
	
	private String getNaverBookSearch(Search search) throws Exception {
		
		String clientId = "y2_rMGDPqrQ3OyZsUfp0";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "1ckgEgYybe";//애플리케이션 클라이언트 시크릿값";
        
		StringBuffer result = new StringBuffer();
		BufferedReader br = null;
		try {
			
			StringBuffer apiURL = new StringBuffer();
			StringBuffer text = new StringBuffer();
            apiURL.append("https://openapi.naver.com/v1/search/book.json");
            text.append("query=").append(URLEncoder.encode(search.getQuery(), "UTF-8"));
            text.append("&start=").append(search.getPage());
            text.append("&display=").append(search.getSize());
			
			
            URL url = new URL(apiURL.toString());
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = text.toString();
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams.toLowerCase());
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
            	result.append(inputLine);
            }
            br.close();
            System.out.println(result.toString());
            
            // 카카오 / Naver 간 데이터 매핑 생략.
            
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
		
		return result.toString();
		
	}
	
	@Override
	public List<MyHistory> getHistory() {
		
		List<MyHistory> list = myHisotoryRepository.findAll(sortByDateDesc());
		
		return list;
	}
	
	@Override
	public List<HitHistory> getHitHistory() {
		
		List<HitHistory> list = hitHisotoryRepository.findAll(sortBySearchCountDesc());
		
		return list;
	}
	
	private Sort sortByDateDesc() {
        return new Sort(Sort.Direction.DESC, "date");
    }
	
	private Sort sortBySearchCountDesc() {
        return new Sort(Sort.Direction.DESC, "searchCount");
    }
	
}
