package com.example.memopad.form;

import java.sql.Date;

import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class MemoDetailForm {

	private Integer memoId;
	private Integer categoryId;
	@Size(min=1, max=200, message="1文字以上200文字以下で指定してください。")
	private String memoTitle;
	@Size(min=1, max=400, message="1文字以上400文字以下で指定してください。")
	private String memoContent;
	private String statusId;
	private String statusName;
	private Date createdAt;
	private Date updatedAt;
	private String modeFlg;
	private boolean initFlg = true;
}
