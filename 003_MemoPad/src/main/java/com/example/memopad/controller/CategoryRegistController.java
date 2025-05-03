package com.example.memopad.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.memopad.entity.Status;
import com.example.memopad.form.CategoryRegistForm;
import com.example.memopad.service.StatusService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CategoryRegistController {
	
	private final StatusService statusService;
	
	// 登録画面表示リクエスト
	@PostMapping("show-category-regist")
	public String showCategoryRegist(
			@ModelAttribute CategoryRegistForm form,
			Model model) {
		
		List<Status> statusList = statusService.findAll();
		model.addAttribute("statusList", statusList);
		
		return "show-category-regist";
	}
	
	// 登録確認画面リクエスト
	@PostMapping("/confirm-category-regist")
	public String confirmCategoryRegist(
			@Validated @ModelAttribute CategoryRegistForm form,
			BindingResult result,
			Model model) {
		
		if(result.hasErrors()) {
			
			List<Status> statusList = statusService.findAll();
			model.addAttribute("statusList", statusList);
			
			return "show-category-regist";
		}
		
		Status status = statusService.findById(form.getStatusId());
		form.setStatusName(status.getStatusName());
		
		return "confirm-category-regist";
	}
}
