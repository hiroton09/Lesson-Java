package com.example.genpassword.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.genpassword.common.GenPasswordConst;
import com.example.genpassword.form.GenTypeForm;

@Service
public class GenPasswordServiceImp implements GenPasswordService {

	@Override
	public String genPassword(GenTypeForm form) {

		String selectText = "";

		// 選択された項目の文字を連結させる
		if (form.isLargeWord()) {
			selectText += GenPasswordConst.LARGE_WORD;
		}

		if (form.isSmallWord()) {
			selectText += GenPasswordConst.SMALL_WORD;
		}

		if (form.isNumber()) {
			selectText += GenPasswordConst.NUMBER;
		}

		if (form.isSymbol()) {
			selectText += GenPasswordConst.SYMBOL;
		}

		String resultText = "";
		while (true) {
			resultText = "";

			// 選択された文字数分処理を繰り返す
			boolean isLargeWordFlg = false;
			boolean isSmallWordFlg = false;
			boolean isNumberFlg = false;
			boolean isSymbolFlg = false;

			for (int i = 0; i < form.getCount(); i++) {
				// ランダムで選択された値を取り出し、返却用文字列に連結させる
				Random random = new Random();
				String selectChar = String.valueOf(selectText.charAt(random.nextInt(selectText.length() - 1)));

				if (!isLargeWordFlg && GenPasswordConst.LARGE_WORD.contains(selectChar)) {
					isLargeWordFlg = true;
				}

				if (!isSmallWordFlg && GenPasswordConst.SMALL_WORD.contains(selectChar)) {
					isSmallWordFlg = true;
				}

				if (!isNumberFlg && GenPasswordConst.NUMBER.contains(selectChar)) {
					isNumberFlg = true;
				}

				if (!isSymbolFlg && GenPasswordConst.SYMBOL.contains(selectChar)) {
					isSymbolFlg = true;
				}

				resultText += selectChar;
			}

			// 選択された項目の文字が1文字以上含まれているか確認する
			boolean resultFlg = checkString(form, isLargeWordFlg, isSmallWordFlg, isNumberFlg, isSymbolFlg);

			if (resultFlg) {
				break;
			}
		}

		return resultText;
	}

	// 文字チェック
	private boolean checkString(GenTypeForm form, boolean isLargeWordFlg, boolean isSmallWordFlg, boolean isNumberFlg,
			boolean isSymbolFlg) {

		if (form.isLargeWord() && !isLargeWordFlg) {
			return false;
		}
		
		if (form.isSmallWord() && !isSmallWordFlg) {
			return false;
		}
		
		if (form.isNumber() && !isNumberFlg) {
			return false;
		}
		
		if (form.isSymbol() && !isSymbolFlg) {
			return false;
		}

		return true;
	}

}
