package com.example.template.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.template.entity.UnitSummaryEntity;

@Mapper
public interface UnitRepository {

	// 単位テーブル、単位詳細テーブルを取得
	public UnitSummaryEntity getUnitSummary();
}
