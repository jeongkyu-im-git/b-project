package com.test.bookproject.controller;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.bookproject.entity.Member;
import com.test.bookproject.model.AjaxResponseSimpleBody;
import com.test.bookproject.service.impl.MemberServiceImpl;
import com.test.bookproject.util.SHA256Util;

@RestController
public class MemberController {

	@Autowired
	private MemberServiceImpl memberService;

	
	@PostMapping("/member/registration")
	public ResponseEntity<?> registration(
			@Valid @RequestBody Member member, Errors errors) throws Exception {
		
		AjaxResponseSimpleBody result = new AjaxResponseSimpleBody();
		
		if ( errors.hasErrors() ) {
			result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
			
			return ResponseEntity.badRequest().body(result);
		}
		
		String msg = memberService.registration(member);
		
		if ( "".equals(msg) ) {
			result.setMsg("회원가입을 축하드립니다.");
			result.setResult(member);
		} else {
			result.setMsg(msg);
			result.setResult(member);
			return ResponseEntity.badRequest().body(result);
		}
		
		return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/member/login")
	public ResponseEntity<?> login(
			@Valid @RequestBody Member loginUser, Errors errors , HttpSession httpSession) throws Exception {
		
		AjaxResponseSimpleBody result = new AjaxResponseSimpleBody();
		
		if ( errors.hasErrors() ) {
			result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
			
			return ResponseEntity.badRequest().body(result);
		}
		
		Member member = memberService.getMemberInfo(loginUser);
		
		int length = Optional.ofNullable(member.getUsername()).map(String::length).orElse(0);
		if ( length == 0 ) {
			result.setMsg("이메일 정보가 유효하지 않습니다.");
			result.setResult(loginUser);
			return ResponseEntity.badRequest().body(result);
		} else {
			
			if ( (member.getPassword()).equals(SHA256Util.getEncrypt(loginUser.getPassword())) ) {
				// 로그인 처리
				result.setMsg("로그인되었습니다.");
				result.setResult(member);
				
				httpSession.setAttribute("LOGIN_USER_NAME", member.getUsername());
				httpSession.setAttribute("LOGIN_EMAIL", member.getEmail());
				
				
			} else {
				result.setMsg("패스워드 불일치");
				result.setResult(loginUser);
				return ResponseEntity.badRequest().body(result);
			}
		}
		
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/member/logout")
	public ResponseEntity<?> logout (
			@Valid @RequestBody Member loginUser, Errors errors , HttpSession httpSession) throws Exception {
		AjaxResponseSimpleBody result = new AjaxResponseSimpleBody();
		
		if ( errors.hasErrors() ) {
			result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
			
			return ResponseEntity.badRequest().body(result);
		}
		
		httpSession.invalidate();
		//httpSession.setAttribute("LOGIN_USER_NAME", member.getUsername());
		//httpSession.setAttribute("LOGIN_EMAIL", member.getEmail());
		
		result.setMsg("로그아웃 되었습니다.");
		
		return ResponseEntity.ok(result);
	}
}
