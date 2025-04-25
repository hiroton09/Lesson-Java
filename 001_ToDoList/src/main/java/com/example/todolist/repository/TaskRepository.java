package com.example.todolist.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.todolist.entity.TaskSummary;

@Mapper
public interface TaskRepository {
	
	// 一覧全件検索
	List<TaskSummary> selectListAll();
}
