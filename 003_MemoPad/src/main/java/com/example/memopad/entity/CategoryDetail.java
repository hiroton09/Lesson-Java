package com.example.memopad.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class CategoryDetail {

	private Integer categoryId;
	private String categoryName;
	private Status status;
	private Integer memoId;
	private String remarks;
	private Date createdAt;
	private Date updatedAt;
	
}
