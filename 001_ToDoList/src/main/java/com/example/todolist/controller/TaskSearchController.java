package com.example.todolist.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.todolist.entity.TaskSummary;
import com.example.todolist.service.TaskService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TaskSearchController {
	
	private final TaskService taskService;

	/*-- 最初のリクエスト --*/
	@GetMapping("/top")
	private String showListSelection() {
		return "task-list";
	}
	

	/*-- 一覧検索リスト --*/
	@PostMapping("/task-search-list")
	private String searchList(Model model) {
		
		List<TaskSummary> list = taskService.findListAll();
		model.addAttribute("taskSummaryList", list);
		
		return "task-list";
	}
}
