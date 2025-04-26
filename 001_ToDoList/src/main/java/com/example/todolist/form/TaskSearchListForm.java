package com.example.todolist.form;

import java.sql.Date;

import lombok.Data;

@Data
public class TaskSearchListForm {
	
	private String taskContents;
	private Integer statusId;
	private Date createdAt;
	private Date updatedAt;
}
