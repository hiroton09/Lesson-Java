package com.example.weatherreport.service;

import java.util.List;

import com.example.weatherreport.entity.WeatherChart;
import com.example.weatherreport.entity.WeatherInfo;

public interface WeatherService {

	// 天気カード情報取得
	public List<WeatherInfo> getWeather(String selectAreaCode);
	
	// 天気グラフ情報取得
	public WeatherChart getWeatherChart(String selectAreaCode);
}
