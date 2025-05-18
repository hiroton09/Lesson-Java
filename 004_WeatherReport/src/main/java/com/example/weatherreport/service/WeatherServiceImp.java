package com.example.weatherreport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.weatherreport.common.GetApiResponse;
import com.example.weatherreport.common.WeatherConst;
import com.example.weatherreport.entity.WeatherInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherServiceImp implements WeatherService {
	
	private final static String TIME_SERIES = "timeSeries";
	private final static String TIME_DEFINS = "timeDefines";
	private final static String AREAS = "areas";
	private final static String AREA = "area";
	private final static String AREA_NAME = "name";
	private final static String WEATHERS = "weatherCodes";

	// 天気情報取得
	@Override
	public List<WeatherInfo> getWeather(String selectArea) {
		
		String url = WeatherConst.WEATHER_API_URL + selectArea + WeatherConst.WEATHER_API_URL_END;
		
		List<WeatherInfo> resultList = new ArrayList<WeatherInfo>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			
			GetApiResponse getApiResponse = new GetApiResponse();
			Map<String, String> response = getApiResponse.getApiResponse(url);
			
			if(WeatherConst.STAUS_CODE_200.equals(response.get(WeatherConst.RESPONSE_CODE))) {
				
				String responseBody = response.get(WeatherConst.RESPONSE_BODY);
//				
//				String repResponseBody = responseBody.substring(1);
//				repResponseBody = responseBody.substring(0, repResponseBody.length() - 1);
//				JsonNode rootNode = mapper.readTree(repResponseBody);
//				System.out.println("レスポンスボディ：" + repResponseBody);
				
				JsonNode rootNode = mapper.readTree(responseBody);
				
				JsonNode timeSeries = rootNode.get(1).get(TIME_SERIES);
				
				int i = 0;
				for(JsonNode timeDefine : timeSeries.get(0).get(TIME_DEFINS)) {
					
					WeatherInfo weatherInfo = new WeatherInfo();
					
					// 日付情報
					weatherInfo.setTimeDefine(timeDefine.asText());

					// エリア情報
					JsonNode areas = timeSeries.get(0).get(AREAS);
					JsonNode area = areas.get(0).get(AREA);
					weatherInfo.setAreaName(area.get(AREA_NAME).asText());
					
					// 天気情報
					JsonNode weathers = areas.get(0).get(WEATHERS);
					weatherInfo.setWeatherCode(weathers.get(i).asText());

					resultList.add(weatherInfo);
					
					i++;
				}
				
			} else {
				String errorMsg = "APIのデータ取得エラー（ステータスコード：" + response.get(WeatherConst.RESPONSE_CODE) + ")";
				throw new Exception(errorMsg);
			}
			
		} catch (Exception e) {
            e.printStackTrace();
        }
		return resultList;
	}
	
	

}
