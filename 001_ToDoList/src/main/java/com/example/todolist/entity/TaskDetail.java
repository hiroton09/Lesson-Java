package com.example.todolist.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class TaskDetail {

	private Integer taskId;
	private String taskContents;
	private Date createdAt;
	private Date updatedAt;
	private Status status;
}
