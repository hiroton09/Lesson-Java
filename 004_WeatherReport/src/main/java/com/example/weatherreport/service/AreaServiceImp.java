package com.example.weatherreport.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.weatherreport.common.GetJsonResource;
import com.example.weatherreport.common.WeatherConst;
import com.example.weatherreport.entity.Area;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AreaServiceImp implements AreaService {

	private final static String OFFICES = "offices";
	private final static String AREA_NAME = "name";

	// エリアリスト取得
	@Override
	public List<Area> getAreaList() {
		
		// JSONファイル読み込み
		GetJsonResource getJsonResource = new GetJsonResource();
		String strAreaList = getJsonResource.getJson(WeatherConst.AREA_LiST);
		
		List<Area> areaList = new ArrayList<Area>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(strAreaList);
		
			JsonNode officesNode = rootNode.get(OFFICES);
			
			// エリアリスト作成
			if (officesNode.isObject()) {
				Iterator<Map.Entry<String, JsonNode>> fields = officesNode.fields();
					while (fields.hasNext()) {

					// エリア情報取得
					Map.Entry<String, JsonNode> officeEntry = fields.next();
					String officeCode = officeEntry.getKey();
					JsonNode officeData = officeEntry.getValue();
					
					Area area = new Area();
					area.setAreaCode(officeCode);
					area.setAreaName(officeData.get(AREA_NAME).asText());
					
					areaList.add(area);
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return areaList;
	}

}
