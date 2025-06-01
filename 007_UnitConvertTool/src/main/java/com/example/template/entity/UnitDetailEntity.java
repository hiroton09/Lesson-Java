package com.example.template.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UnitDetailEntity {

	private Integer unitId;
	private BigDecimal unitDetailId;
	private String unitDetailName;
	private String unitDetailValue;
}
