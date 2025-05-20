package com.example.weatherreport.entity;

import lombok.Data;

@Data
public class WeatherDetail {

	private String iconDayTime;
	private String iconDayNight;
	private String code;
	private String weatherName;
	private String weatherNameEnglish;
}
