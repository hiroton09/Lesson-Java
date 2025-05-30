package com.example.weatherreport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.weatherreport.common.GetApiResponse;
import com.example.weatherreport.common.GetJsonResource;
import com.example.weatherreport.common.WeatherConst;
import com.example.weatherreport.entity.WeatherChart;
import com.example.weatherreport.entity.WeatherDetail;
import com.example.weatherreport.entity.WeatherInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
	private final static String TEMPS_MIN = "tempsMin";
	private final static String TEMPS_MAX = "tempsMax";

	// 天気カード情報取得
	@Override
	public List<WeatherInfo> getWeather(String selectAreaCode) {
		
		String url = WeatherConst.WEATHER_API_URL + selectAreaCode + WeatherConst.WEATHER_API_URL_END;
		
		List<WeatherInfo> resultList = new ArrayList<WeatherInfo>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			
			GetApiResponse getApiResponse = new GetApiResponse();
			Map<String, String> response = getApiResponse.getApiResponse(url);
			
			if(WeatherConst.STAUS_CODE_200.equals(response.get(WeatherConst.RESPONSE_CODE))) {
				
				String responseBody = response.get(WeatherConst.RESPONSE_BODY);
				
				JsonNode rootNode = mapper.readTree(responseBody);
				
				JsonNode timeSeries = rootNode.get(1).get(TIME_SERIES);
				
				// 天気カード情報取得
				int i = 0;
				for(JsonNode timeDefine : timeSeries.get(0).get(TIME_DEFINS)) {
					
					WeatherInfo weatherInfo = new WeatherInfo();
					
					// 日付情報
					String strTimeDefine = timeDefine.asText().substring(0, 10);
					weatherInfo.setTimeDefine(strTimeDefine);

					// エリア情報
					JsonNode areas = timeSeries.get(0).get(AREAS);
					JsonNode area = areas.get(0).get(AREA);
					weatherInfo.setAreaName(area.get(AREA_NAME).asText());
					
					// 天気情報
					JsonNode weathers = areas.get(0).get(WEATHERS);
					weatherInfo.setWeatherCode(weathers.get(i).asText());
					
					// 天気コードに紐づく天気名を取得
					WeatherDetail weatherDetail = getWeatherDetail(weathers.get(i).asText());
					weatherInfo.setWeatherName(weatherDetail.getWeatherName());
					weatherInfo.setWeatherIcon(weatherDetail.getIconDayTime());

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
	
	// 天気グラフ情報取得
	@Override
	public WeatherChart getWeatherChart(String selectAreaCode) {
		
		String url = WeatherConst.WEATHER_API_URL + selectAreaCode + WeatherConst.WEATHER_API_URL_END;
		
		WeatherChart weatherChart = new WeatherChart();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			GetApiResponse getApiResponse = new GetApiResponse();
			Map<String, String> response = getApiResponse.getApiResponse(url);
			
			if(WeatherConst.STAUS_CODE_200.equals(response.get(WeatherConst.RESPONSE_CODE))) {
				
				String responseBody = response.get(WeatherConst.RESPONSE_BODY);
				
				JsonNode rootNode = mapper.readTree(responseBody);
				
				JsonNode timeSeries = rootNode.get(1).get(TIME_SERIES);
				
				// 天気グラフ情報取得
				// 日付情報
				List<String> dateList = new ArrayList<String>();
				for(JsonNode timeDefine : timeSeries.get(1).get(TIME_DEFINS)) {
					dateList.add(timeDefine.asText().substring(0, 10));
				}

				String[] strTimeDefineList = dateList.toArray(new String[dateList.size()]);
				weatherChart.setDate(strTimeDefineList);
				
				// 最低気温情報
				List<String> tempsMinList = new ArrayList<String>();
				for(JsonNode tempsMin : timeSeries.get(1).get(AREAS).get(0).get(TEMPS_MIN)) {
					tempsMinList.add(tempsMin.asText());
				}
				
				weatherChart.setTempsMin(tempsMinList.toArray(new String[tempsMinList.size()]));
				
				// 最高気温情報
				List<String> tempsMaxList = new ArrayList<String>();
				for(JsonNode tempsMax : timeSeries.get(1).get(AREAS).get(0).get(TEMPS_MAX)) {
					tempsMaxList.add(tempsMax.asText());
				}

				weatherChart.setTempsMax(tempsMaxList.toArray(new String[tempsMaxList.size()]));

				JsonNode areas = timeSeries.get(1).get(AREAS);
				
				// タイトル
				String startDate = strTimeDefineList[0];
				String endDate = strTimeDefineList[strTimeDefineList.length - 1];
				String dateBetween = startDate + " ～ " + endDate;
				JsonNode area = areas.get(0).get(AREA);
				weatherChart.setChartTitle(area.get(AREA_NAME).asText() + " ： " + dateBetween);
				
			} else {
				String errorMsg = "APIのデータ取得エラー（ステータスコード：" + response.get(WeatherConst.RESPONSE_CODE) + ")";
				throw new Exception(errorMsg);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weatherChart;
	}
	
	// 天気情報を取得
	private WeatherDetail getWeatherDetail(String weatherCode) throws JsonMappingException, JsonProcessingException {
		
		GetJsonResource getJsonResource = new GetJsonResource();
		String weatherCodeList = getJsonResource.getJson(WeatherConst.WEATHER_CODE_LiST);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(weatherCodeList);
		
		JsonNode weatherNode = rootNode.get(weatherCode);
		
		WeatherDetail weatherDetail = new WeatherDetail();
		weatherDetail.setIconDayTime(weatherNode.get(0).asText());
		weatherDetail.setIconDayNight(weatherNode.get(1).asText());
		weatherDetail.setCode(weatherNode.get(2).asText());
		weatherDetail.setWeatherName(weatherNode.get(3).asText());
		weatherDetail.setWeatherNameEnglish(weatherNode.get(4).asText());
		
		return weatherDetail;
	}

}
