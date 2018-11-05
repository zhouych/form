package com.zyc.form.data;

import java.lang.reflect.Field;
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
		AreaExcludes areaExcludes = null;
		if(formtype != null) {
			Class<?> clazz = formtype.getClass();
			Field field = null;
			try {
				field = clazz.getField(formtype.name());
			} catch (NoSuchFieldException | SecurityException e) {
				throw new RuntimeException(e);
			}
			
			if(field.isAnnotationPresent(AreaExcludes.class)) {
				areaExcludes = field.getAnnotation(AreaExcludes.class);
			}
		}
		
		List<EntryBean> list = new ArrayList<EntryBean>();
		
		for (FormArea area : FormArea.values()) {
			boolean exclude = false;
			if(areaExcludes != null && areaExcludes.values() != null && areaExcludes.values().length > 0) {
				for (FormArea excludeArea : areaExcludes.values()) {
					if(area.equals(excludeArea)) {
						exclude = true;
						break;
					}
				}
			}
			
			if(!exclude) {
				list.add(new EntryBean(area.getValue(), area.getText()));
			}
		}
		
		return list;
	}
}
