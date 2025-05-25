package com.example.genpassword.common;

import com.example.genpassword.form.GenTypeForm;

public class GenPasswordUtil {
	
	// 何も選択されず生成ボタンを押下した場合
	public static boolean checkType(GenTypeForm form) {
		if(!form.isLargeWord() && !form.isSmallWord() && !form.isNumber() && !form.isSymbol()) {
			return false;
		}
		
		return true;
	}

	// 選択された種別の数と出力する文字数の判定
	public static boolean checkLength(GenTypeForm form) {
		boolean resultFlg = false;
		int selectTypeCount = 0;
		
		if(form.isLargeWord()) {
			selectTypeCount++;
		}
		
		if(form.isSmallWord()) {
			selectTypeCount++;
		}
		
		if(form.isNumber()) {
			selectTypeCount++;
		}
		
		if(form.isSymbol()) {
			selectTypeCount++;
		}
		
		if(selectTypeCount <= form.getCount()) {
			resultFlg = true;
		}
		
		return resultFlg;
	}
}
