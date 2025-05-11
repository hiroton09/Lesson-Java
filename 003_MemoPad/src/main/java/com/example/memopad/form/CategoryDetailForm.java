package com.example.memopad.form;

import java.sql.Date;
import java.util.List;

import jakarta.validation.constraints.Size;

import com.example.memopad.entity.Memo;

import lombok.Data;

@Data
public class CategoryDetailForm {

	private Integer categoryId;
	@Size(min=1, max=200, message="1文字以上200文字以下で指定してください。")
	private String categoryName;
	private String statusId;
	private String statusName;
	@Size(max=400, message="400文字以下で指定してください。")
	private String remarks;
	private Date createdAt;
	private Date updatedAt;
	private String modeFlg;
	private boolean initFlg = true;
	private List<Memo> memoList;
}
