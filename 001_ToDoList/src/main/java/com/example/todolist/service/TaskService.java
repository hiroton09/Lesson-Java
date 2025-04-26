package com.example.todolist.service;

import java.util.List;

import com.example.todolist.entity.Task;
import com.example.todolist.entity.TaskSummary;

public interface TaskService {
	
	// 一覧全件検索
	List<TaskSummary> findListAll();
	
	// 一覧条件検索
	List<TaskSummary> findListByConditions(Task task);
	
	// 新規登録
	void regist(Task task);
}
