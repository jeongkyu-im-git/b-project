package com.test.bookproject.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.bookproject.entity.HitHistory;
import com.test.bookproject.entity.MyHistory;
import com.test.bookproject.entity.Search;
import com.test.bookproject.model.AjaxResponseSimpleBody;
import com.test.bookproject.service.impl.SearchServiceImpl;

@RestController
public class SearchController {

	@Autowired
	private SearchServiceImpl searchService;
	
	@RequestMapping("/book/search")
	public ResponseEntity<?> search(@Valid @RequestBody Search search, Errors errors, HttpSession httpSession) throws Exception {
		
		AjaxResponseSimpleBody result = new AjaxResponseSimpleBody();
		
		if ( errors.hasErrors() ) {
			result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
			
			return ResponseEntity.badRequest().body(result);
		}
		
		String email = (String)httpSession.getAttribute("LOGIN_EMAIL");
		
		int length = Optional.ofNullable(email).map(String::length).orElse(0);
		System.out.println(email);
		System.out.println(length);
		if ( length == 0 ) {
			result.setMsg("로그인 사용자만 이용 가능합니다. 로그인 후 이용해주시기 바랍니다.");
			return ResponseEntity.badRequest().body(result);
		} else {
			String list = searchService.query(search, email);
			return ResponseEntity.ok(list);
		}
	}
	
	@RequestMapping("/book/myhistory")
	public ResponseEntity<?> myhistory(@Valid @RequestBody Search search, Errors errors, HttpSession httpSession) throws Exception {
		
		AjaxResponseSimpleBody result = new AjaxResponseSimpleBody();
		
		if ( errors.hasErrors() ) {
			result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
			
			return ResponseEntity.badRequest().body(result);
		}
		
		String email = (String)httpSession.getAttribute("LOGIN_EMAIL");
		
		int length = Optional.ofNullable(email).map(String::length).orElse(0);
		System.out.println(email);
		System.out.println(length);
		if ( length == 0 ) {
			result.setMsg("로그인 사용자만 이용 가능합니다. 로그인 후 이용해주시기 바랍니다.");
			return ResponseEntity.badRequest().body(result);
		} else {
			List<MyHistory> list = searchService.getHistory();
			result.setMsg("조회되었습니다.");
			result.setResult(list);
			return ResponseEntity.ok(list);
		}
	}
	
	@RequestMapping("/book/hithistory")
	public ResponseEntity<?> hithistory(@Valid @RequestBody Search search, Errors errors, HttpSession httpSession) throws Exception {
		
		AjaxResponseSimpleBody result = new AjaxResponseSimpleBody();
		
		if ( errors.hasErrors() ) {
			result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
			
			return ResponseEntity.badRequest().body(result);
		}
		
		String email = (String)httpSession.getAttribute("LOGIN_EMAIL");
		
		int length = Optional.ofNullable(email).map(String::length).orElse(0);
		
		if ( length == 0 ) {
			result.setMsg("로그인 사용자만 이용 가능합니다. 로그인 후 이용해주시기 바랍니다.");
			return ResponseEntity.badRequest().body(result);
		} else {
			List<HitHistory> list = searchService.getHitHistory();
			result.setMsg("조회되었습니다.");
			result.setResult(list);
			return ResponseEntity.ok(list);
		}
	}

	
}
