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
import com.example.todolist.form.TaskRegistForm;
import com.example.todolist.service.StatusService;
import com.example.todolist.service.TaskService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TaskRegistContoller {
	
	private final StatusService statusService;
	private final TaskService taskService;
	
	/*-- タスク登録画面表示 --*/
	@PostMapping("/task-show-regist")
	public String showRegist(@ModelAttribute TaskRegistForm form, Model model) {
		
		List<Status> list = statusService.findAll();
		model.addAttribute("statusList", list);
		
		return "task-regist";
	}
	
	/*-- タスク登録リクエスト --*/
	@PostMapping("/task-regist")
	public String regist(
			@Validated @ModelAttribute TaskRegistForm form,
			BindingResult result, Model model) {
		
		// 入力エラーがある場合、タスク登録画面に戻す
		if(result.hasErrors()) {
			
			List<Status> list = statusService.findAll();
			model.addAttribute("statusList", list);
			
			return "task-regist";
		}
		
		Status status = statusService.findById(form.getStatusId());
		form.setStatusName(status.getStatusName());
		
		// 正常な場合、タスク登録確認画面に遷移する
		return "task-confirm-regist";
	}
	
	/*-- タスク登録確認リクエスト --*/
	@PostMapping("/task-confirm-regist")
	public String confirmRegist(
			@Validated @ModelAttribute TaskRegistForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {
		
		// 入力エラーがある場合、タスク登録画面に戻す
		if(result.hasErrors()) {
			
			List<Status> list = statusService.findAll();
			model.addAttribute("statusList", list);
			
			return "task-regist";
		}
		
		// form -> entityへ
		Task task = new Task();
		task.setTaskContents(form.getTaskContents());
		task.setStatusId(form.getStatusId());
		
		// 登録処理
		taskService.regist(task);
		
		// フラッシュスコープに完了メッセージを表示してリダイレクト
		redirectAttributes.addFlashAttribute("msg", "(タスク登録)");
		
		// 正常な場合、タスク登録確認画面に遷移する
		return "redirect:/task-complete";
	}
}
