package com.example.weatherreport.service;

import java.util.List;

import com.example.weatherreport.entity.WeatherInfo;

public interface WeatherService {

	// 天気情報取得
	public List<WeatherInfo> getWeather(String area);
}
