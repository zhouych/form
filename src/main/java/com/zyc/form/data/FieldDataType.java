package com.zyc.form.data;

public enum FieldDataType {
	STRING("String", "文本型"),
	INTEGER("Integer", "整数型"),
	DOUBLE("Double", "金额型"),
	DATE("Date", "日期型");
	
	private String value;
	private String text;

	private FieldDataType(String value, String text) {
		this.value = value;
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public String getText() {
		return text;
	}
}
