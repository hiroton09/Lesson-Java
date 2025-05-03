package com.example.memopad.form;

import java.sql.Date;

import lombok.Data;

@Data
public class CategorySearchListForm {

	private Integer categoryId;
	private String categoryName;
	private String statusId;
	private Date createdAt;
	private Date updatedAt;
}
