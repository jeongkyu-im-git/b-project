package com.test.bookproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.bookproject.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}
