package com.example.weatherreport.common;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class GetJsonResource {

	public String getJson(String resourceName) {
		
		String path = "classpath:jsons/" + resourceName + ".json";
		
		ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(path);
        
        try (InputStream inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes());
        } catch (IOException e) {
            String errorMsg = "JSONファイルの読み込みに失敗しました (String): " + resourceName + " - " + e.getMessage();
            return errorMsg;
        }
	}
}
