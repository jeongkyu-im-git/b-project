package com.test.bookproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.bookproject.entity.HitHistory;

public interface HitHistoryRepository extends JpaRepository<HitHistory, String> {

}
