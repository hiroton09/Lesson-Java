package com.example.memopad.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.memopad.entity.Status;
import com.example.memopad.form.CategorySearchListForm;
import com.example.memopad.service.StatusService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CategorySearchController {
	
	private final StatusService statusService;

	@GetMapping("/top")
	public String showCategoryList(@ModelAttribute CategorySearchListForm form, Model model) {
		
		List<Status> statusList = statusService.findAll();
		model.addAttribute("statusList", statusList);
		
		return "category-list";
	}
}
