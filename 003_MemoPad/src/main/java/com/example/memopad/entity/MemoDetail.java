package com.example.memopad.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class MemoDetail {

	private Integer memoId;
	private String memoTitle;
	private String memoContent;
	private Status status;
	private Integer categoryId;
	private Date createdAt;
	private Date updatedAt;
}
