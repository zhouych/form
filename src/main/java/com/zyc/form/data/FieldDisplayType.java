package com.zyc.form.data;

public enum FieldDisplayType {
	TEXTBOX("textbox", "文本框"),
	TEXTAREA("textarea", "文本域"),
	SELECT("select","下拉框"),
	TREE("tree","树形"),
	IMAGE("image", "图片"),
	BUTTON("button", "按钮"),
	HYPERLINK("hyperlink", "超链接");

	private String value;
	private String text;

	private FieldDisplayType(String value, String text) {
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
