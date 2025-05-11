package com.example.memopad.entity;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class CategoryDetail {

	private Integer categoryId;
	private String categoryName;
	private Status status;
	private String remarks;
	private Date createdAt;
	private Date updatedAt;
	private List<Memo> memoList;
	
}
