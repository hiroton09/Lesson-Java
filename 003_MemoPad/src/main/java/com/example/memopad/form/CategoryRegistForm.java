package com.example.memopad.form;

import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class CategoryRegistForm {

	@Size(min=1, max=200, message="1文字以上200文字以下で指定してください。")
	private String categoryName;
	private String statusId;
	private String statusName;
	@Size(max=400, message="400文字以下で指定してください。")
	private String remarks;
}
