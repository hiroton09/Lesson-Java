package com.example.memopad.service;

import java.util.List;

import com.example.memopad.entity.Status;

public interface StatusService {
	
	// 全件取得
	List<Status> findAll();

	// ステータスIDに紐づくステータス取得
	Status findById(String statusId);
}
