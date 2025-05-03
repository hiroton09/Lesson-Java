package com.example.memopad.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class Status {

	private String statusId;
	private String statusName;
	private Date createdAt;
	private Date updatedAt;
}
