package com.example.template.service;

import java.util.List;

import com.example.template.entity.UnitSummaryEntity;

public interface UnitConvertToolService {

	// 単位情報を取得
	List<UnitSummaryEntity> getUnitSummary();
}
