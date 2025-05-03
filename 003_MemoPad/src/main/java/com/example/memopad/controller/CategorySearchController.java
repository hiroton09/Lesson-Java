package com.example.memopad.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.memopad.entity.Category;
import com.example.memopad.entity.CategorySummary;
import com.example.memopad.entity.Status;
import com.example.memopad.form.CategorySearchListForm;
import com.example.memopad.service.CategoryService;
import com.example.memopad.service.StatusService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CategorySearchController {
	
	private final StatusService statusService;
	private final CategoryService categoryService;

	// 初期リクエスト
	@GetMapping("/top")
	public String showCategoryList(
			@ModelAttribute CategorySearchListForm form,
			Model model) {
		
		List<Status> statusList = statusService.findAll();
		model.addAttribute("statusList", statusList);
		
		return "category-list";
	}
	
	// 条件検索による一覧表示
	@PostMapping("/category-search-list")
	public String selectCategoryList(
			@Validated @ModelAttribute CategorySearchListForm form,
			BindingResult result,
			Model model) {

		// 検索パラメータをもとにDB検索
		// form → entity
		Category category = new Category();
		category.setCategoryId(form.getCategoryId());
		if(!form.getCategoryName().equals("")) {
			category.setCategoryName("%" + form.getCategoryName() + "%");			
		}
		if(!form.getStatusId().equals("")) {
			category.setStatusId(form.getStatusId());
		}
		category.setCreatedAt(form.getCreatedAt());
		category.setUpdatedAt(form.getUpdatedAt());
		
		List<CategorySummary> categorySummaryList = categoryService.findListByCondition(category);
		
		List<Status> statusList = statusService.findAll();
		model.addAttribute("statusList", statusList);
		model.addAttribute("categorySummaryList", categorySummaryList);
		
		return "category-list";
	}
}
