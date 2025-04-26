package com.example.todolist.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class Task {

	private Integer taskId;
	private String taskContents;
	private Integer statusId;
	private Date createdAt;
	private Date updatedAt;
}
