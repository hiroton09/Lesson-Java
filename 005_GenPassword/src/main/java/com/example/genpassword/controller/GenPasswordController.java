package com.example.genpassword.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.genpassword.form.GenTypeForm;

@Controller
public class GenPasswordController {

	// 初期表示リクエスト
	@GetMapping("/top")
	public String initShow(@ModelAttribute GenTypeForm form){
		return "index";
	}
	
	// パスワード生成リクエスト
	@GetMapping("/gen-password")
	public String genPassword() {
		return "index";
	}
}
