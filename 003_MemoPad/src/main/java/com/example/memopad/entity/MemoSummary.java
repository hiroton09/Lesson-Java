package com.example.memopad.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class MemoSummary {

	private Integer memoId;
	private String memoTitle;
	private Status status;
	private Integer categoryId;
	private Date createdAt;
	private Date updatedAt;
}
