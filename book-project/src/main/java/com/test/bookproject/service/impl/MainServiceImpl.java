package com.test.bookproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.bookproject.entity.Member;
import com.test.bookproject.repository.MemberRepository;
import com.test.bookproject.service.MainService;
import com.test.bookproject.util.SHA256Util;

@Service("mainService")
public class MainServiceImpl implements MainService {

	@Autowired
	MemberRepository memberRepository;

	@Override
	public void init() {
		
		String encPass = SHA256Util.getEncrypt("12345678");
		System.out.println(encPass);
		// ADD Member
		memberRepository.save(new Member("jeongkyu.im@gmail.com",encPass,"임정규"));
		
	}
	
}
