package com.example.template.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.template.entity.UnitSummaryEntity;

@Mapper
public interface UnitRepository {

	// 単位テーブル、単位詳細テーブルを取得
	public List<UnitSummaryEntity> getUnitSummary();
}
