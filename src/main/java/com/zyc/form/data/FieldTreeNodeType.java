package com.zyc.form.data;

import com.zyc.baselibs.vo.EntryBeanable;

public enum FieldTreeNodeType implements EntryBeanable {
	/**
	 * 表单区域
	 */
	AREA("area", "表单区域"),
	
	/**
	 * 表单字段
	 */
	FIELD("field", "表单字段");
	
	private String value;
	private String text;

	private FieldTreeNodeType(String value, String text) {
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
