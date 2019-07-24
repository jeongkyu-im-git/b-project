package com.test.bookproject.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.bookproject.entity.Member;
import com.test.bookproject.repository.MemberRepository;
import com.test.bookproject.service.MemberService;
import com.test.bookproject.util.SHA256Util;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberRepository memberRepository;
	
	@Override
	public String registration(Member member) {
		// TODO Auto-generated method stub
		
		String msg = "";
		
		if ( memberRepository.exists(member.getEmail()) ) {
			msg = "이미 존재하는 이메일입니다.";
		} else {
			
			// 패스워드 암호화
			member.setPassword(SHA256Util.getEncrypt(member.getPassword()));
			
			memberRepository.save(member);
			
			msg = "";
		}
		
		return msg;
	}
	
	@Override
	public Member getMemberInfo(Member member) {
		// TODO Auto-generated method stub
		
		Member memberInfo = Optional.ofNullable(memberRepository.findOne(member.getEmail())).orElseGet(() -> new Member());
		
		return memberInfo;
	}
	
}
