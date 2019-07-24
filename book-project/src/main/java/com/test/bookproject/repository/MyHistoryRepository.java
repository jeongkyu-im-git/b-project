package com.test.bookproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.bookproject.entity.MyHistory;

public interface MyHistoryRepository extends JpaRepository<MyHistory, String> {

}
