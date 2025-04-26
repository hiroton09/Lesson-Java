package com.example.todolist.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.todolist.entity.Task;
import com.example.todolist.entity.TaskDetail;
import com.example.todolist.entity.TaskSummary;

@Mapper
public interface TaskRepository {
	
	// 一覧全件検索
	List<TaskSummary> selectListAll();
	
	// 一覧条件検索
	List<TaskSummary> selectListByConditions(@Param("task") Task task);
	
	// 詳細検索
	TaskDetail selectDetailByTaskId(@Param("taskId") Integer taskId);
	
	// 新規登録
	void insert(@Param("task") Task task);
}
