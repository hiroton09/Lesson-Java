package com.example.memopad.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.memopad.entity.Category;
import com.example.memopad.entity.CategoryDetail;
import com.example.memopad.entity.Status;
import com.example.memopad.form.CategoryDetailForm;
import com.example.memopad.service.CategoryService;
import com.example.memopad.service.StatusService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CategoryDetailController {
	
	private final String READ = "read";
	private final String REGIST = "regist";
	private final String UPDATE = "update";
	private final String DELETE = "delete";
	
	private final StatusService statusService;
	private final CategoryService categoryService;
	
	// 画面表示リクエスト(詳細)
	@PostMapping(value="show-category-detail", params="read")
	public String showCategoryDetailRead(
			@ModelAttribute CategoryDetailForm form,
			Model model) {
		
		CategoryDetail categoryDetail = categoryService.findDetailByCategoryId(form.getCategoryId());
		
		form.setCategoryId(categoryDetail.getCategoryId());
		form.setCategoryName(categoryDetail.getCategoryName());
		form.setStatusId(categoryDetail.getStatus().getStatusId());
		form.setStatusName(categoryDetail.getStatus().getStatusName());
		form.setRemarks(categoryDetail.getRemarks());
		form.setCreatedAt(categoryDetail.getCreatedAt());
		form.setUpdatedAt(categoryDetail.getUpdatedAt());
		form.setModeFlg(READ);
		
		return "category-detail";
	}
	
	// 画面表示リクエスト(登録)
	@PostMapping(value="show-category-detail", params="regist")
	public String showCategoryDetailRegist(
			@ModelAttribute CategoryDetailForm form,
			Model model) {
		
		List<Status> statusList = statusService.findAll();
		model.addAttribute("statusList", statusList);
		
		form.setModeFlg(REGIST);
		
		return "category-detail";
	}
	
	// 確認画面リクエスト(登録)
	@PostMapping(value="/confirm-category-detail", params="regist")
	public String confirmCategoryDetailRegist(
			@Validated @ModelAttribute CategoryDetailForm form,
			BindingResult result,
			Model model) {

		form.setModeFlg(REGIST);
		
		if(result.hasErrors()) {
			
			List<Status> statusList = statusService.findAll();
			model.addAttribute("statusList", statusList);
			
			return "category-detail";
		}
		
		Status status = statusService.findById(form.getStatusId());
		form.setStatusName(status.getStatusName());
		
		return "category-detail-confirm";
	}
	
	// 処理リクエスト(登録)
	@PostMapping(value="/category-detail", params="regist")
	public String categoryDetailRegist(
			@Validated @ModelAttribute CategoryDetailForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {
		
		if(result.hasErrors()) {
			
			form.setModeFlg(REGIST);
			
			List<Status> statusList = statusService.findAll();
			model.addAttribute("statusList", statusList);
			
			return "category-detail";
		}
		
		Category category = new Category();
		category.setCategoryName(form.getCategoryName());
		category.setStatusId(form.getStatusId());
		category.setRemarks(form.getRemarks());
		
		// 登録処理
		categoryService.regist(category);
		
		redirectAttributes.addFlashAttribute("msg", "カテゴリー登録完了");
		
		return "redirect:/category-complete";
	}
	
	// 画面表示リクエスト(更新)
	@PostMapping(value="show-category-detail", params="update")
	public String showCategoryDetailUpdate(
			@ModelAttribute CategoryDetailForm form,
			Model model) {
		
		CategoryDetail categoryDetail = categoryService.findDetailByCategoryId(form.getCategoryId());
		form.setCategoryId(categoryDetail.getCategoryId());
		form.setCategoryName(categoryDetail.getCategoryName());
		form.setStatusId(categoryDetail.getStatus().getStatusId());
		form.setStatusName(categoryDetail.getStatus().getStatusName());
		form.setRemarks(categoryDetail.getRemarks());
		form.setCreatedAt(categoryDetail.getCreatedAt());
		form.setUpdatedAt(categoryDetail.getUpdatedAt());
		
		List<Status> statusList = statusService.findAll();
		model.addAttribute("statusList", statusList);
		
		form.setModeFlg(UPDATE);
		
		return "category-detail";
	}
	
	// 画面表示リクエスト(削除)
	@PostMapping(value="show-category-detail", params="delete")
	public String showCategoryDetailDelete(
			@ModelAttribute CategoryDetailForm form,
			Model model) {
		
		CategoryDetail categoryDetail = categoryService.findDetailByCategoryId(form.getCategoryId());
		form.setCategoryId(categoryDetail.getCategoryId());
		form.setCategoryName(categoryDetail.getCategoryName());
		form.setStatusId(categoryDetail.getStatus().getStatusId());
		form.setStatusName(categoryDetail.getStatus().getStatusName());
		form.setRemarks(categoryDetail.getRemarks());
		form.setCreatedAt(categoryDetail.getCreatedAt());
		form.setUpdatedAt(categoryDetail.getUpdatedAt());
		
		form.setModeFlg(DELETE);
		
		return "category-detail";
	}
}
