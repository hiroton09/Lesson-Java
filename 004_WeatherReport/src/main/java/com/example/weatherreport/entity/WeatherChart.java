package com.example.weatherreport.entity;

import lombok.Data;

@Data
public class WeatherChart {

	private String chartTitle;
	private String[] date;
	private String[] tempsMin;
	private String[] tempsMax;
}
