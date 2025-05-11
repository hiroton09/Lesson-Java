package com.example.memopad.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.memopad.entity.Memo;
import com.example.memopad.entity.MemoDetail;
import com.example.memopad.entity.Status;
import com.example.memopad.form.MemoDetailForm;
import com.example.memopad.service.MemoService;
import com.example.memopad.service.StatusService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemoDetailController {
	
	private final String READ = "read";
	private final String REGIST = "regist";
	private final String UPDATE = "update";
	private final String DELETE = "delete";
	
	private final StatusService statusService;
	private final MemoService memoService;
	
	// 画面表示リクエスト(詳細)
	@PostMapping(value="show-memo-detail", params="read")
	public String showMemoDetailRead(
			@ModelAttribute MemoDetailForm form,
			Model model) {
		
		MemoDetail memoDetail = memoService.findDetailByMemoId(form.getMemoId());
		
		form = setForm(memoDetail, form);
		form.setModeFlg(READ);
		
		return "memo-detail";
	}
	
	// 画面表示リクエスト(登録)
	@PostMapping(value="show-memo-detail", params="regist")
	public String showMemoDetailRegist(
			@ModelAttribute MemoDetailForm form,
			Model model) {
		
		List<Status> statusList = statusService.findAll();
		model.addAttribute("statusList", statusList);
		
		form.setModeFlg(REGIST);
		
		return "memo-detail";
	}
	
	// 確認画面リクエスト(登録)
	@PostMapping(value="/confirm-memo-detail", params="regist")
	public String confirmMemoDetailRegist(
			@Validated @ModelAttribute MemoDetailForm form,
			BindingResult result,
			Model model) {

		form.setModeFlg(REGIST);
		
		if(result.hasErrors()) {
			
			List<Status> statusList = statusService.findAll();
			model.addAttribute("statusList", statusList);
			
			return "memo-detail";
		}
		
		Status status = statusService.findById(form.getStatusId());
		form.setStatusName(status.getStatusName());
		
		return "memo-detail-confirm";
	}
	
	// 処理リクエスト(登録)
	@PostMapping(value="/memo-detail", params="regist")
	public String memoDetailRegist(
			@Validated @ModelAttribute MemoDetailForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {
		
		if(result.hasErrors()) {
			
			form.setModeFlg(REGIST);
			
			List<Status> statusList = statusService.findAll();
			model.addAttribute("statusList", statusList);
			
			return "memo-detail";
		}
		
		Memo memo = new Memo();
		memo.setMemoTitle(form.getMemoTitle());
		memo.setMemoContent(form.getMemoContent());
		memo.setCategoryId(form.getCategoryId());
		memo.setStatusId(form.getStatusId());
		
		// 登録処理
		memoService.regist(memo);
		
		redirectAttributes.addFlashAttribute("msg", "メモ登録完了");
		
		return "redirect:/memo-complete";
	}
	
	// 画面表示リクエスト(更新)
	@PostMapping(value="show-memo-detail", params="update")
	public String showMemoDetailUpdate(
			@ModelAttribute MemoDetailForm form,
			Model model) {

		MemoDetail memoDetail = memoService.findDetailByMemoId(form.getMemoId());
		
		if(form.isInitFlg()) {
			form = setForm(memoDetail, form);
		}

		form.setModeFlg(UPDATE);
		form.setInitFlg(false);
		
		List<Status> statusList = statusService.findAll();
		model.addAttribute("statusList", statusList);
		
		return "memo-detail";
	}
	
	// 確認画面リクエスト(更新)
	@PostMapping(value="/confirm-memo-detail", params="update")
	public String confirmMemoDetailUpdate(
			@Validated @ModelAttribute MemoDetailForm form,
			BindingResult result,
			Model model) {

		form.setModeFlg(UPDATE);
		
		if(result.hasErrors()) {
			
			List<Status> statusList = statusService.findAll();
			model.addAttribute("statusList", statusList);
			
			return "memo-detail";
		}
		
		MemoDetail memoDetail = memoService.findDetailByMemoId(form.getMemoId());
		form.setCreatedAt(memoDetail.getCreatedAt());
		form.setUpdatedAt(memoDetail.getUpdatedAt());
		
		Status status = statusService.findById(form.getStatusId());
		form.setStatusName(status.getStatusName());
		
		return "memo-detail-confirm";
	}
	
	// 処理リクエスト(更新)
	@PostMapping(value="/memo-detail", params="update")
	public String memoDetailUpdate(
			@Validated @ModelAttribute MemoDetailForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {
		
		if(result.hasErrors()) {
			
			form.setModeFlg(UPDATE);
			
			List<Status> statusList = statusService.findAll();
			model.addAttribute("statusList", statusList);
			
			return "memo-detail";
		}
		
		Memo memo = setEntity(form);
		
		// 更新処理
		memoService.update(memo);
		
		redirectAttributes.addFlashAttribute("msg", "メモ更新完了");
		
		return "redirect:/memo-complete";
	}
	
	// 画面表示リクエスト(削除)
	@PostMapping(value="show-memo-detail", params="delete")
	public String showMemoDetailDelete(
			@ModelAttribute MemoDetailForm form,
			Model model) {
		
		MemoDetail memoDetail = memoService.findDetailByMemoId(form.getMemoId());
		
		form = setForm(memoDetail, form);
		form.setModeFlg(DELETE);
		
		return "memo-detail";
	}

	// 確認画面リクエスト(削除)
	@PostMapping(value="/confirm-memo-detail", params="delete")
	public String confirmMemoDetailDelete(
			@Validated @ModelAttribute MemoDetailForm form,
			BindingResult result,
			Model model) {
		
		MemoDetail memoDetail = memoService.findDetailByMemoId(form.getMemoId());
		
		form = setForm(memoDetail, form);
		form.setModeFlg(DELETE);
		
		return "memo-detail-confirm";
	}
	
	// 処理リクエスト(削除)
	@PostMapping(value="/memo-detail", params="delete")
	public String memoDetailDelete(
			@Validated @ModelAttribute MemoDetailForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {

		// 削除処理
		memoService.delete(form.getMemoId());
		
		redirectAttributes.addFlashAttribute("msg", "メモ削除完了");
		
		return "redirect:/memo-complete";
	}
	
	
	// entity -> form
	private MemoDetailForm setForm(MemoDetail memoDetail, MemoDetailForm form) {
		
		form.setMemoId(memoDetail.getMemoId());
		form.setMemoTitle(memoDetail.getMemoTitle());
		form.setMemoContent(memoDetail.getMemoContent());
		form.setCategoryId(memoDetail.getCategoryId());
		form.setStatusId(memoDetail.getStatus().getStatusId());
		form.setStatusName(memoDetail.getStatus().getStatusName());
		form.setCreatedAt(memoDetail.getCreatedAt());
		form.setUpdatedAt(memoDetail.getUpdatedAt());
		
		return form;
	}
	
	// form -> entity
	private Memo setEntity(MemoDetailForm form) {
		
		Memo memo = new Memo();
		
		memo.setMemoId(form.getMemoId());
		memo.setMemoTitle(form.getMemoTitle());
		memo.setMemoContent(form.getMemoContent());
		memo.setCategoryId(form.getCategoryId());
		memo.setStatusId(form.getStatusId());
		memo.setCreatedAt(form.getCreatedAt());
		memo.setUpdatedAt(form.getUpdatedAt());
		
		return memo;
	}
}
