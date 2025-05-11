package com.example.memopad.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.memopad.entity.Memo;
import com.example.memopad.entity.MemoDetail;
import com.example.memopad.entity.MemoSummary;
import com.example.memopad.repository.MemoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemoServiceImp implements MemoService {

	private final MemoRepository memoRepository;
	
	// 条件検索による一覧取得
	@Override
	@Transactional(readOnly = true)
	public List<MemoSummary> findListByCondition(Memo memo) {
		
		List<MemoSummary> list = memoRepository.selectListByCondition(memo);
		
		return list;
	}
	
	// 詳細検索
	@Override
	@Transactional(readOnly = true)
	public MemoDetail findDetailByMemoId(Integer memoId) {
		
		MemoDetail detail = memoRepository.selectDetailByMemoId(memoId);
		
		return detail;
	}

	// 登録
	@Override
	@Transactional
	public void regist(Memo memo) {
		
		memoRepository.insert(memo);
	}

	// 更新
	@Override
	@Transactional
	public void update(Memo memo) {

		memoRepository.update(memo);
		
	}

	// 削除
	@Override
	@Transactional
	public void delete(Integer memoId) {
		
		memoRepository.delete(memoId);
		
	}

	// カテゴリーIDに紐づくメモ削除
	@Override
	@Transactional
	public void deleteByCategoryId(Integer categoryId) {
		
		memoRepository.deleteByCategoryId(categoryId);
		
	}

}
