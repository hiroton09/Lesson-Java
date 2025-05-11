package com.example.memopad.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.memopad.entity.Memo;
import com.example.memopad.entity.MemoDetail;
import com.example.memopad.entity.MemoSummary;

@Mapper
public interface MemoRepository {
	
	// 条件検索による一覧取得
	List<MemoSummary> selectListByCondition(@Param("memo") Memo memo);
	
	// 詳細検索
	MemoDetail selectDetailByMemoId(@Param("memoId") Integer memoId);
	
	// 登録
	void insert(@Param("memo") Memo memo);
	
	// 更新
	void update(@Param("memo") Memo memo);
	
	// 削除
	void delete(@Param("memoId") Integer memoId);
}
