package com.example.weatherreport.common;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class GetApiResponse {
	
	public Map<String, String> getApiResponse(String apiUrl) throws IOException, InterruptedException {
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();
		
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		resultMap.put(WeatherConst.RESPONSE_CODE, String.valueOf(response.statusCode()));
		resultMap.put(WeatherConst.RESPONSE_BODY, response.body());
		
		return resultMap;
	}

}
