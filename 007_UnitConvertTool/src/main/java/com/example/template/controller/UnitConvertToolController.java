package com.example.template.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.template.entity.UnitSummaryEntity;

@Controller
public class UnitConvertToolController {

	@GetMapping("/top")
	public String init(Model model) {
		
		UnitSummaryEntity unitSummary = new UnitSummaryEntity();
		model.addAttribute("unitSummary", unitSummary);
		
		return "index";
	}
}
