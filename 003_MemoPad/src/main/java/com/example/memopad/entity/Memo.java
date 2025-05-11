package com.example.memopad.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class Memo {

	private Integer memoId;
	private String memoTitle;
	private String memoContent;
	private String statusId;
	private Integer categoryId;
	private Date createdAt;
	private Date updatedAt;
}
