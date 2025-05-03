package com.example.memopad.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class Category {

	private Integer categoryId;
	private String categoryName;
	private String statusId;
	private Integer memoId;
	private String remarks;
	private Date createdAt;
	private Date updatedAt;
}
