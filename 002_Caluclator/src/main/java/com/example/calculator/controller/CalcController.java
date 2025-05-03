package com.example.calculator.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalcController {
	
	@GetMapping("/top")
	public String showCalc(Model model) {
		
		// 数字表示項目
		List<Integer> numberList = new ArrayList<>();
		for(int i = 9; i >= 0; i--) {
			numberList.add(i);
		}
		
		// 記号表示項目
		List<String> markList = new ArrayList<>();
		markList.add("C");
		markList.add("+");
		markList.add("-");
		markList.add("*");
		markList.add("/");
		markList.add("=");
		markList.add(".");
		
		model.addAttribute("numberList", numberList);
		model.addAttribute("markList", markList);
		
		return "show-calc";
	}
}
