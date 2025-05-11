package com.example.memopad.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class CategorySummary {

	private Integer categoryId;
	private String categoryName;
	private Status status;
	private Date createdAt;
	private Date updatedAt;
}
