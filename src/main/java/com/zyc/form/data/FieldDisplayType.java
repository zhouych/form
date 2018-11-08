package com.zyc.form.data;

import java.util.ArrayList;
import java.util.List;

import com.zyc.baselibs.vo.EntryBean;
import com.zyc.baselibs.web.EmptyNodeType;

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

	public static List<EntryBean> toList(EmptyNodeType empty) {
		List<EntryBean> list = new ArrayList<EntryBean>();
		for (FieldDisplayType type : FieldDisplayType.values()) {
			list.add(new EntryBean(type.getValue(), type.getText()));
		}
		
		if(empty != null) {
			list.add(0, new EntryBean(empty.getValue(), empty.getText()));
		}
		
		return list;
	}
}
