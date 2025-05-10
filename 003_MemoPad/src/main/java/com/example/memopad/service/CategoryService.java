package com.example.memopad.service;

import java.util.List;

import com.example.memopad.entity.Category;
import com.example.memopad.entity.CategoryDetail;
import com.example.memopad.entity.CategorySummary;

public interface CategoryService {
	
	// 条件検索による一覧取得
	List<CategorySummary> findListByCondition(Category category);
	
	// 詳細検索
	CategoryDetail findDetailByCategoryId(Integer categoryId);
	
	// 登録
	void regist(Category category);
}
