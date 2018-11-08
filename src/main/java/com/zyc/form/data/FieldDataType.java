package com.zyc.form.data;

import java.util.ArrayList;
import java.util.List;

import com.zyc.baselibs.vo.EntryBean;
import com.zyc.baselibs.web.EmptyNodeType;

public enum FieldDataType {
	STRING("String", "文本型"),
	INTEGER("Integer", "整数型"),
	BIGDECIMAL("BigDecimal", "高精度型"),
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

	public static List<EntryBean> toList(EmptyNodeType empty) {
		List<EntryBean> list = new ArrayList<EntryBean>();
		for (FieldDataType type : FieldDataType.values()) {
			list.add(new EntryBean(type.getValue(), type.getText()));
		}
		
		if(empty != null) {
			list.add(0, new EntryBean(empty.getValue(), empty.getText()));
		}
		
		return list;
	}
}
