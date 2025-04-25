package com.example.todolist.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class Status {
	
	private Integer statusId;
	private String statusName;
	private Date createdAt;
	private Date updatedAt;
}
