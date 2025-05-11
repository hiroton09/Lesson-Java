package com.example.memopad.service;

import java.util.List;

import com.example.memopad.entity.Memo;
import com.example.memopad.entity.MemoDetail;
import com.example.memopad.entity.MemoSummary;

public interface MemoService {
	
	// 条件検索による一覧取得
	List<MemoSummary> findListByCondition(Memo memo);
	
	// 詳細検索
	MemoDetail findDetailByMemoId(Integer memoId);
	
	// 登録
	void regist(Memo memo);
	
	// 更新
	void update(Memo memo);
	
	// 削除
	void delete(Integer memoId);
	
	// カテゴリーIDに紐づくメモ削除
	void deleteByCategoryId(Integer categoryId);
}
