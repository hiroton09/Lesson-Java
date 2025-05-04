package com.example.memopad.service;

import java.util.List;

import com.example.memopad.entity.Category;
import com.example.memopad.entity.CategorySummary;

public interface CategoryService {
	
	// 条件検索による一覧取得
	List<CategorySummary> findListByCondition(Category category);
	
	// 登録
	void regist(Category category);
}
