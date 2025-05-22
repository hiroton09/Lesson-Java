package com.example.genpassword.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GenPasswordController {

	@GetMapping("/top")
	public String initShow(){
		return "index";
	}
}
