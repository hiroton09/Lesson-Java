package com.example.todolist.service;

import java.util.List;

import com.example.todolist.entity.TaskSummary;

public interface TaskService {
	
	// 一覧全件検索
	List<TaskSummary> findListAll();
}
