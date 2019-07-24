package com.test.bookproject.service;

import com.test.bookproject.entity.Member;

public interface MemberService {

	public String registration(Member member);
	
	public Member getMemberInfo(Member member);
	
}
