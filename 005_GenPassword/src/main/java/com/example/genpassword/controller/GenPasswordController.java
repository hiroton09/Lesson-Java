package com.example.genpassword.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.genpassword.common.GenPasswordConst;
import com.example.genpassword.common.GenPasswordUtil;
import com.example.genpassword.form.GenTypeForm;
import com.example.genpassword.service.GenPasswordService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GenPasswordController {
	
	private final GenPasswordService genPasswordService;

	// 初期表示リクエスト
	@GetMapping("/top")
	public String initShow(@ModelAttribute GenTypeForm form){
		return "index";
	}
	
	// パスワード生成リクエスト
	@PostMapping("/gen-password")
	public String genPassword(@Validated @ModelAttribute GenTypeForm form,
			BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			return "index";
		}
		
		// 何も選択されず生成ボタンを押下した場合
		if(!GenPasswordUtil.checkType(form)) {
			model.addAttribute(GenPasswordConst.ERR_MSG, GenPasswordConst.ERR_MSG_01);
			return "index";
		}
		
		// 文字数が0以下の場合
		if(form.getCount() <= 0) {
			model.addAttribute(GenPasswordConst.ERR_MSG, GenPasswordConst.ERR_MSG_02);
			return "index";			
		}
		
		
		// 選択された種別の数と出力する文字数の判定
		if(!GenPasswordUtil.checkLength(form)) {
			model.addAttribute(GenPasswordConst.ERR_MSG, GenPasswordConst.ERR_MSG_03);
			return "index";
		}
		
		
		// 画面で選択された項目をもとにパスワードを生成
		String genPassword = genPasswordService.genPassword(form);
		
		model.addAttribute("genPassword", genPassword);
		
		return "index";
	}
}
