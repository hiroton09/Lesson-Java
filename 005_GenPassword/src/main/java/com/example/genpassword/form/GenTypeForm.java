package com.example.genpassword.form;

import lombok.Data;

@Data
public class GenTypeForm {

	private boolean largeWord;
	private boolean smallWord;
	private boolean number;
	private boolean symbol;
	private int count;
}
