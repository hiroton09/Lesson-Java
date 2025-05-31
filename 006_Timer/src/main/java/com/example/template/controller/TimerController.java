package com.example.template.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TimerController {

	@GetMapping("/top")
	public String init(Model model) {
		
		model.addAttribute("timerDisplayTextHH", "00");
		model.addAttribute("timerDisplayTextMM", "00");
		model.addAttribute("timerDisplayTextSS", "00");
		model.addAttribute("swDisplayText", "00:00:00:00");
		
		return "index";
	}
}
