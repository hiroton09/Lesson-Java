package com.example.todolist.form;

import java.sql.Date;

import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class TaskEditForm {
	
	private Integer taskId;
	
	@Size(min=1, max=400, message="1文字から400文字で指定してください。")
	private String taskContents;
	
	private Integer statusId;
	
	private String statusName;
	
	private Date createdAt;
	
	private Date updatedAt;
}
