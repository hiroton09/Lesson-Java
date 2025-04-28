package com.example.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.todolist.entity.TaskDetail;
import com.example.todolist.form.TaskRemoveForm;
import com.example.todolist.service.TaskService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TaskRemoveContoller {
	
	private final TaskService taskService;
	
	/*-- タスク削除リクエスト --*/
	@PostMapping("/task-remove")
	public String remove(
			@ModelAttribute TaskRemoveForm form,
			Model model) {
		
		// 削除前のタスク詳細取得
		TaskDetail taskDetail = taskService.findDetailByTaskId(form.getTaskId());
		
		model.addAttribute("taskDetail", taskDetail);
		
		// タスク削除確認画面に遷移する
		return "task-confirm-remove";
	}
	
	/*-- タスク削除確認リクエスト --*/
	@PostMapping("/task-confirm-remove")
	public String confirmRemove(
			@Validated @ModelAttribute TaskRemoveForm form,
			RedirectAttributes redirectAttributes) {
		
		// 削除処理
		taskService.remove(form.getTaskId());
		
		// フラッシュスコープに完了メッセージを表示してリダイレクト
		redirectAttributes.addFlashAttribute("msg", "(タスク削除)");
		
		// 正常な場合、タスク削除完了画面に遷移する
		return "redirect:/task-complete";
	}
}
