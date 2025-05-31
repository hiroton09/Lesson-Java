package com.example.template.entity;

import java.util.List;

import lombok.Data;

@Data
public class UnitSummaryEntity {

	private Integer unitId;
	private String unitName;
	private List<UnitDetailEntity> unitDetailList;
}
