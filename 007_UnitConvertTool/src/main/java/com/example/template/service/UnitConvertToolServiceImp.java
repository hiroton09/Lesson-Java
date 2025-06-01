package com.example.template.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.template.entity.UnitSummaryEntity;
import com.example.template.repository.UnitRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnitConvertToolServiceImp implements UnitConvertToolService {

	private final UnitRepository unitRepository;
	
	// 単位情報を取得
	@Override
	@Transactional(readOnly = true)
	public List<UnitSummaryEntity> getUnitSummary() {
		
		List<UnitSummaryEntity> unitSummary = unitRepository.getUnitSummary();
		
		return unitSummary;
	}

}
