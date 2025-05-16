package com.example.weatherreport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeatherController {
	
	// 表示リクエスト
	@GetMapping("/top")
	public String showWeather() {
		return "weather";
	}

}
