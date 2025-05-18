package com.example.weatherreport.entity;

import lombok.Data;

@Data
public class WeatherInfo {

	private String timeDefine;
	private String areaName;
	private String weatherCode;
	private String weatherName;
	private String weatherIcon;
}
