package com.example.memopad.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.memopad.entity.Category;
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

}
