package com.example.todolist.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.todolist.entity.Task;
import com.example.todolist.entity.TaskSummary;
import com.example.todolist.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImp implements TaskService {
	
	private final TaskRepository taskRepository;

	@Override
	@Transactional(readOnly = true)
	public List<TaskSummary> findListAll() {
		 
		List<TaskSummary> list = taskRepository.selectListAll();
		
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskSummary> findListByConditions(Task task) {
		
		List<TaskSummary> list = taskRepository.selectListByConditions(task);
		
		return list;
	}
	
	@Override
	@Transactional
	public void regist(Task task) {
		
		taskRepository.insert(task);
	}
}
