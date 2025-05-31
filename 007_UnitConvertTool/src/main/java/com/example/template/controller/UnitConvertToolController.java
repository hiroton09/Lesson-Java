package com.example.template.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UnitConvertToolController {

	@GetMapping("/top")
	public String init() {
		return "index";
	}
}
