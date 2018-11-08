package com.zyc.form.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zyc.baselibs.vo.EntryBean;

public enum FormArea {
	
	MAIN("Main", "主表区"),
	
	DETAIL("Detail", "明细区"), 
	
	REVERSAL("Reversal", "冲销区"),
	
	PAYMENT("Payment", "付款区"),
	
	REPAYMENT("Repayment", "还款区"),
	
	BACKUP01("Backup01", "备用区一"),
	
	BACKUP02("Backup02", "备用区二"),
	
	BACKUP03("Backup03", "备用区三"),
	
	BACKUP04("Backup04", "备用区四");
	
	private String value;
	private String text;

	private FormArea(String value, String text) {
		this.value = value;
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public String getText() {
		return text;
	}
	
	public static Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (FormArea area : FormArea.values()) {
			map.put(area.getValue(), area.getText());
		}
		return map;
	}

	public static List<EntryBean> toList() {
		return toList(null);
	}
	
	public static List<EntryBean> toList(FormType formtype) {
		List<EntryBean> list = new ArrayList<EntryBean>();
		for (FormArea area : FormArea.values()) {
			if(formtype == null || !formtype.isExclude(area)) {
				list.add(new EntryBean(area.getValue(), area.getText()));
			}
		}
		return list;
	}
	
	public static List<String> values(FormType formtype) {
		List<String> list = new ArrayList<String>();
		for (FormArea area : FormArea.values()) {
			if(formtype == null || !formtype.isExclude(area)) {
				list.add(area.getValue());
			}
		}
		return list;
	}
}
