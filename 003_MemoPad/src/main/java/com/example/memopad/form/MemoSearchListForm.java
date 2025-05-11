package com.example.memopad.form;

import java.sql.Date;

import lombok.Data;

@Data
public class MemoSearchListForm {

	private Integer memoId;
	private String memoTitle;
	private String statusId;
	private Date createdAt;
	private Date updatedAt;
}
