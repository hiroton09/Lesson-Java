package com.example.todolist.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.todolist.entity.Status;
import com.example.todolist.entity.Task;
import com.example.todolist.entity.TaskDetail;
import com.example.todolist.entity.TaskSummary;
import com.example.todolist.form.TaskSearchDetailForm;
import com.example.todolist.form.TaskSearchListForm;
import com.example.todolist.service.StatusService;
import com.example.todolist.service.TaskService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TaskSearchController {
	
	private final TaskService taskService;
	private final StatusService statusService;

	/*-- 最初のリクエスト --*/
	@GetMapping("/top")
	private String showListSelection(@ModelAttribute TaskSearchListForm form,
			Model model) {
		
		// ステータスリストを設定
		List<Status> list = statusService.findAll();
		model.addAttribute("statusList", list);
		
		return "task-list";
	}

	/*-- 一覧検索リスト --*/
	@PostMapping("/task-search-list")
	private String searchList(@Validated @ModelAttribute TaskSearchListForm form,
			BindingResult result,
			Model model) {
		
		// form -> entityへ
		Task task = new Task();
		if(!form.getTaskContents().equals("")) {
			task.setTaskContents("%" + form.getTaskContents() + "%");
		}
		task.setStatusId(form.getStatusId());
		task.setCreatedAt(form.getCreatedAt());
		task.setUpdatedAt(form.getUpdatedAt());
		
		List<TaskSummary> list = taskService.findListByConditions(task);
		
		// ステータスリストを設定
		List<Status> statusList = statusService.findAll();
		model.addAttribute("statusList", statusList);
		
		// 検索結果を格納
		model.addAttribute("taskSummaryList", list);
		
		return "task-list";
	}

	/*-- タスク詳細 --*/
	@PostMapping("/task-search-detail")
	private String searchDetail(TaskSearchDetailForm form,
			Model model) {
		
		// 詳細検索
		TaskDetail taskDetail = taskService.findDetailByTaskId(form.getTaskId());
		
		// 検索結果を格納
		model.addAttribute("taskDetail", taskDetail);
		
		return "task-detail";
	}
}
