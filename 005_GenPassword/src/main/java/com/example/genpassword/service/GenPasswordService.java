package com.example.genpassword.service;

import com.example.genpassword.form.GenTypeForm;

public interface GenPasswordService {

	// パスワードを生成
	String genPassword(GenTypeForm form);
}
