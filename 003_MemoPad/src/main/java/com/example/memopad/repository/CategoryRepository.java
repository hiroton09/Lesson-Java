package com.example.memopad.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.memopad.entity.Category;
import com.example.memopad.entity.CategorySummary;

@Mapper
public interface CategoryRepository {
	
	// 条件検索による一覧取得
	List<CategorySummary> selectListByCondition(@Param("category") Category category);
	
	// 登録
	void insert(@Param("category") Category category);

}
