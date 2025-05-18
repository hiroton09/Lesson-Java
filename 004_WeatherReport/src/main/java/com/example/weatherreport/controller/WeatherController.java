package com.example.weatherreport.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.weatherreport.entity.WeatherInfo;
import com.example.weatherreport.form.SearchWeatherForm;
import com.example.weatherreport.service.WeatherService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WeatherController {
	
	private final WeatherService weatherService;
	
	// 表示リクエスト
	@GetMapping("/top")
	public String showWeather(@ModelAttribute SearchWeatherForm form, Model model) {
		return "weather";
	}

	// 天気情報取得
	@PostMapping("/search-weather")
	public String searchWeather(@Validated @ModelAttribute SearchWeatherForm form, Model model) {
		
		List<WeatherInfo> weatherInfoList = weatherService.getWeather(form.getArea());
		
		model.addAttribute("weatherInfoList", weatherInfoList);
		
		return "weather";
	}
}
