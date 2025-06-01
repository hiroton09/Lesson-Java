package com.example.template.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.template.entity.UnitSummaryEntity;
import com.example.template.service.UnitConvertToolService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UnitConvertToolController {
	
	private final UnitConvertToolService unitConvertToolService;

	@GetMapping("/top")
	public String init(Model model) {
		
		List<UnitSummaryEntity> unitSummary = unitConvertToolService.getUnitSummary();
		model.addAttribute("unitSummary", unitSummary);
		
		return "index";
	}
}
