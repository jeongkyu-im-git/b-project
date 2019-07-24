package com.test.bookproject;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.bookproject.entity.Member;
import com.test.bookproject.repository.MemberRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;
	
	@After
	public void cleanup() {
		memberRepository.deleteAll();
	}
	
	@Test
	public void memberSave() {
		
		// given
		memberRepository.save(new Member("aaa@aaa.com","password111", "임정규"));
//		memberRepository.save
		
		// when
		List<Member> memberList = memberRepository.findAll();
		
		// then
		Member member = memberList.get(0);
		System.out.println(member.getEmail());
		System.out.println(member.getPassword());
		System.out.println(member.getUsername());
		assertThat(member.getEmail(), is("aaa@aaa.com"));
		assertThat(member.getPassword(), is("password111"));
		assertThat(member.getUsername(), is("임정규"));
		
	}
}
