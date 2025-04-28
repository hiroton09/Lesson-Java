package com.example.todolist.form;

import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class TaskRegistForm {
	
	@Size(min=1, max=400, message="1文字から400文字で指定してください。")
	private String taskContents;
	
	private Integer statusId;
	
	private String statusName;
}
