package com.zyc.form.data;

import java.util.HashMap;
import java.util.Map;


public enum FormType {
	GENERAL("general", "普通单"),
	APPLICATION("application", "申请单"), 
	REIMBURSE("reimburse", "报销单"), 
	LOAN("loan", "借款单"), 
	REPAYMENT("repayment", "还款单");

	private String value;
	private String text;

	private FormType(String value, String text) {
		this.value = value;
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public static Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (FormType type : FormType.values()) {
			map.put(type.getValue(), type.getText());
		}
		return map;
	}
}
