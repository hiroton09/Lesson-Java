package com.example.memopad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

	// カテゴリー関連の処理完了後のリダイレクト先
	@GetMapping("/category-complete")
	public String completeCategory() {
		
		return "category-complete";
	}
	
	// メモ関連の処理完了後のリダイレクト先
	@GetMapping("/memo-complete")
	public String completeMemo() {
		
		return "memo-complete";
	}
}
