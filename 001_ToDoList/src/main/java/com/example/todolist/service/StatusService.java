package com.example.todolist.service;

import java.util.List;

import com.example.todolist.entity.Status;

public interface StatusService {
	
	// 一覧検索
	List<Status> findAll();
	
	// 1権取得
	Status findById(Integer statusId);
}
