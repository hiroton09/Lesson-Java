package com.example.memopad.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.memopad.entity.Status;

@Mapper
public interface StatusRepository {
	
	// 全件検索
	List<Status> selectAll();
	
	// ステータスIDに紐づく条件検索
	Status selectById(@Param("statusId") String statusId);

}
