package com.example.todolist.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.todolist.entity.Status;
import com.example.todolist.entity.Task;
import com.example.todolist.form.TaskEditForm;
import com.example.todolist.service.StatusService;
import com.example.todolist.service.TaskService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TaskEditContoller {
	
	private final StatusService statusService;
	private final TaskService taskService;
	
	/*-- タスク編集画面表示 --*/
	@PostMapping("/task-show-edit")
	public String showEdit(@ModelAttribute TaskEditForm form, Model model) {
		
		List<Status> list = statusService.findAll();
		model.addAttribute("statusList", list);
		
		return "task-edit";
	}
	
	/*-- タスク更新リクエスト --*/
	@PostMapping("/task-edit")
	public String edit(
			@Validated @ModelAttribute TaskEditForm form,
			BindingResult result, Model model) {
		
		// 入力エラーがある場合、タスク編集画面に戻す
		if(result.hasErrors()) {
			
			List<Status> list = statusService.findAll();
			model.addAttribute("statusList", list);
			
			return "task-edit";
		}
		
		Status status = statusService.findById(form.getStatusId());
		form.setStatusName(status.getStatusName());
		
		// 正常な場合、タスク更新確認画面に遷移する
		return "task-confirm-edit";
	}
	
	/*-- タスク更新確認リクエスト --*/
	@PostMapping("/task-confirm-edit")
	public String confirmEdit(
			@Validated @ModelAttribute TaskEditForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {
		
		// 入力エラーがある場合、タスク編集画面に戻す
		if(result.hasErrors()) {
			
			List<Status> list = statusService.findAll();
			model.addAttribute("statusList", list);
			
			return "task-edit";
		}
		
		// form -> entityへ
		Task task = new Task();
		task.setTaskId(form.getTaskId());
		task.setTaskContents(form.getTaskContents());
		task.setStatusId(form.getStatusId());
		task.setCreatedAt(form.getCreatedAt());
		task.setUpdatedAt(form.getUpdatedAt());
		
		// 更新処理
		taskService.update(task);
		
		// フラッシュスコープに完了メッセージを表示してリダイレクト
		redirectAttributes.addFlashAttribute("msg", "(タスク更新)");
		
		// 正常な場合、タスク登録確認画面に遷移する
		return "redirect:/task-complete";
	}
}
