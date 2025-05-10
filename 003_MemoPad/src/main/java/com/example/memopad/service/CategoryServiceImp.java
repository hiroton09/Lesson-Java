package com.example.memopad.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.memopad.entity.Category;
import com.example.memopad.entity.CategoryDetail;
import com.example.memopad.entity.CategorySummary;
import com.example.memopad.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

	private final CategoryRepository categoryRepository;
	
	// 条件検索による一覧取得
	@Override
	@Transactional(readOnly = true)
	public List<CategorySummary> findListByCondition(Category category) {
		
		List<CategorySummary> list = categoryRepository.selectListByCondition(category);
		
		return list;
	}
	
	// 詳細検索
	@Override
	@Transactional(readOnly = true)
	public CategoryDetail findDetailByCategoryId(Integer categoryId) {
		
		CategoryDetail detail = categoryRepository.selectDetailByCategoryId(categoryId);
		
		return detail;
	}

	// 登録
	@Override
	@Transactional
	public void regist(Category category) {
		
		categoryRepository.insert(category);
	}

}
