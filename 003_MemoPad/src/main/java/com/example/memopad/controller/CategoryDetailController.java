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
import com.example.memopad.entity.Memo;
import com.example.memopad.entity.MemoSummary;
import com.example.memopad.entity.Status;
import com.example.memopad.form.CategoryDetailForm;
import com.example.memopad.service.CategoryService;
import com.example.memopad.service.MemoService;
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
	private final MemoService memoService;
	
	// 画面表示リクエスト(詳細)
	@PostMapping(value="show-category-detail", params="read")
	public String showCategoryDetailRead(
			@ModelAttribute CategoryDetailForm form,
			Model model) {
		
		CategoryDetail categoryDetail = categoryService.findDetailByCategoryId(form.getCategoryId());
		
		form = setForm(categoryDetail, form);
		form.setModeFlg(READ);

		model.addAttribute("memoList", categoryDetail.getMemoList());
		
		List<Status> statusList = statusService.findAll();
		model.addAttribute("statusList", statusList);
		
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
		
		if(form.isInitFlg()) {
			form = setForm(categoryDetail, form);
		}

		form.setModeFlg(UPDATE);
		form.setInitFlg(false);
		form.setCreatedAt(categoryDetail.getCreatedAt());
		form.setUpdatedAt(categoryDetail.getUpdatedAt());

		model.addAttribute("memoList", categoryDetail.getMemoList());
		
		List<Status> statusList = statusService.findAll();
		model.addAttribute("statusList", statusList);
		
		return "category-detail";
	}
	
	// 確認画面リクエスト(更新)
	@PostMapping(value="/confirm-category-detail", params="update")
	public String confirmCategoryDetailUpdate(
			@Validated @ModelAttribute CategoryDetailForm form,
			BindingResult result,
			Model model) {

		form.setModeFlg(UPDATE);

		CategoryDetail categoryDetail = categoryService.findDetailByCategoryId(form.getCategoryId());
		
		if(result.hasErrors()) {

			model.addAttribute("memoList", categoryDetail.getMemoList());
			
			List<Status> statusList = statusService.findAll();
			model.addAttribute("statusList", statusList);
			
			return "category-detail";
		}
		
		form.setCreatedAt(categoryDetail.getCreatedAt());
		form.setUpdatedAt(categoryDetail.getUpdatedAt());
		
		Status status = statusService.findById(form.getStatusId());
		form.setStatusName(status.getStatusName());
		
		return "category-detail-confirm";
	}
	
	// 処理リクエスト(更新)
	@PostMapping(value="/category-detail", params="update")
	public String categoryDetailUpdate(
			@Validated @ModelAttribute CategoryDetailForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {
		
		if(result.hasErrors()) {
			
			form.setModeFlg(UPDATE);
			
			CategoryDetail categoryDetail = categoryService.findDetailByCategoryId(form.getCategoryId());
			model.addAttribute("memoList", categoryDetail.getMemoList());
			
			List<Status> statusList = statusService.findAll();
			model.addAttribute("statusList", statusList);
			
			return "category-detail";
		}
		
		Category category = setEntity(form);
		
		// 更新処理
		categoryService.update(category);
		
		redirectAttributes.addFlashAttribute("msg", "カテゴリー更新完了");
		
		return "redirect:/category-complete";
	}
	
	// 画面表示リクエスト(削除)
	@PostMapping(value="show-category-detail", params="delete")
	public String showCategoryDetailDelete(
			@ModelAttribute CategoryDetailForm form,
			Model model) {
		
		CategoryDetail categoryDetail = categoryService.findDetailByCategoryId(form.getCategoryId());
		
		form = setForm(categoryDetail, form);
		form.setModeFlg(DELETE);
		
		model.addAttribute("memoList", categoryDetail.getMemoList());
		
		return "category-detail";
	}

	// 確認画面リクエスト(削除)
	@PostMapping(value="/confirm-category-detail", params="delete")
	public String confirmCategoryDetailDelete(
			@Validated @ModelAttribute CategoryDetailForm form,
			BindingResult result,
			Model model) {
		
		CategoryDetail categoryDetail = categoryService.findDetailByCategoryId(form.getCategoryId());
		
		form = setForm(categoryDetail, form);
		form.setModeFlg(DELETE);
		
		model.addAttribute("memoList", categoryDetail.getMemoList());
		
		return "category-detail-confirm";
	}
	
	// 処理リクエスト(削除)
	@PostMapping(value="/category-detail", params="delete")
	public String categoryDetailDelete(
			@Validated @ModelAttribute CategoryDetailForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {

		// 削除処理
		memoService.deleteByCategoryId(form.getCategoryId());
		categoryService.delete(form.getCategoryId());
		
		redirectAttributes.addFlashAttribute("msg", "カテゴリー削除完了");
		
		return "redirect:/category-complete";
	}
	
	// 条件検索によるメモ一覧表示
	@PostMapping("/memo-search-list")
	public String selectMemoList(
			@Validated @ModelAttribute CategoryDetailForm form,
			BindingResult result,
			Model model) {
		
		CategoryDetail categoryDetail = categoryService.findDetailByCategoryId(form.getCategoryId());
		
		form = setForm(categoryDetail, form);
		form.setModeFlg(READ);

		// 検索パラメータをもとにDB検索
		// form → entity
		Memo memo = new Memo();
		memo.setMemoId(form.getMemoSearchListForm().getMemoId());
		if(!form.getMemoSearchListForm().getMemoTitle().equals("")) {
			memo.setMemoTitle("%" + form.getMemoSearchListForm().getMemoTitle() + "%");			
		}
		if(!form.getMemoSearchListForm().getStatusId().equals("")) {
			memo.setStatusId(form.getMemoSearchListForm().getStatusId());
		}
		memo.setCreatedAt(form.getMemoSearchListForm().getCreatedAt());
		memo.setUpdatedAt(form.getMemoSearchListForm().getUpdatedAt());
		memo.setCategoryId(form.getCategoryId());
		
		List<MemoSummary> memoList = memoService.findListByCondition(memo);
		
		List<Status> statusList = statusService.findAll();
		model.addAttribute("statusList", statusList);
		model.addAttribute("memoList", memoList);
		
		return "category-detail";
	}
	
	
	// entity -> form
	private CategoryDetailForm setForm(CategoryDetail categoryDetail, CategoryDetailForm form) {
		
		form.setCategoryId(categoryDetail.getCategoryId());
		form.setCategoryName(categoryDetail.getCategoryName());
		form.setStatusId(categoryDetail.getStatus().getStatusId());
		form.setStatusName(categoryDetail.getStatus().getStatusName());
		form.setRemarks(categoryDetail.getRemarks());
		form.setCreatedAt(categoryDetail.getCreatedAt());
		form.setUpdatedAt(categoryDetail.getUpdatedAt());
		
		return form;
	}
	
	// form -> entity
	private Category setEntity(CategoryDetailForm form) {
		
		Category category = new Category();
		
		category.setCategoryId(form.getCategoryId());
		category.setCategoryName(form.getCategoryName());
		category.setStatusId(form.getStatusId());
		category.setRemarks(form.getRemarks());
		category.setCreatedAt(form.getCreatedAt());
		category.setUpdatedAt(form.getUpdatedAt());
		
		return category;
	}
}
