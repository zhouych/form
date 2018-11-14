package com.zyc.form.data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zyc.baselibs.data.EmptyNodeType;
import com.zyc.baselibs.vo.EntryBean;
import com.zyc.baselibs.vo.EntryBeanable;

public enum FormType implements EntryBeanable {
	
	/**
	 * 普通单：用于一般性事项，与费用无直接关系的单据。例如，会议室申请单、请假申请单等等
	 */
	@AreaExcludes(values = { FormArea.REVERSAL, FormArea.PAYMENT, FormArea.REPAYMENT })	
	GENERAL("general", "普通单"),
	
	/**
	 * 申请单：用于包含费用支出的申请单。例如，出差申请单、采购申请单等等
	 */
	@AreaExcludes(values = { FormArea.REVERSAL, FormArea.PAYMENT, FormArea.REPAYMENT })
	APPLICATION("application", "申请单"),

	/**
	 * 报销单
	 */
	@AreaExcludes(values = { FormArea.REPAYMENT })
	REIMBURSE("reimburse", "报销单"), 

	/**
	 * 借款单
	 */
	@AreaExcludes(values = { FormArea.REPAYMENT })
	LOAN("loan", "借款单"),

	/**
	 * 还款单
	 */
	@AreaExcludes(values = { FormArea.PAYMENT })
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

	public String getText() {
		return text;
	}
	
	public static Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (FormType type : FormType.values()) {
			map.put(type.getValue(), type.getText());
		}
		return map;
	}
	
	public static List<EntryBean> toList() {
		List<EntryBean> list = new ArrayList<EntryBean>();
		for (FormType type : FormType.values()) {
			list.add(new EntryBean(type.getValue(), type.getText()));
		}
		return list;
	}

	public static List<EntryBean> toList(EmptyNodeType empty) {
		List<EntryBean> list = new ArrayList<EntryBean>();
		for (FormType type : FormType.values()) {
			list.add(new EntryBean(type.getValue(), type.getText()));
		}
		
		if(empty != null) {
			list.add(0, new EntryBean(empty.getValue(), empty.getText()));
		}
		
		return list;
	}
	
	public static FormType from(String value) {
		return Enum.valueOf(FormType.class, value.toUpperCase());
	}
	
	public AreaExcludes getAreaExcludes() {
		Field field = null;
		try {
			field = this.getClass().getField(this.name());
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException(e);
		}
		
		return field.isAnnotationPresent(AreaExcludes.class) ? field.getAnnotation(AreaExcludes.class) : null;
	}
	
	/**
	 * 指定的表单区域是否被当前表单类型排除（即当前表单是否不包括指定的表单区域）。
	 * @param area 指定的表单区域
	 * @return Boolean: true-不包括; false-包括
	 */
	public boolean isExclude(FormArea area) {
		AreaExcludes areaExcludes = this.getAreaExcludes();
		if(areaExcludes != null && areaExcludes.values() != null && areaExcludes.values().length > 0) {
			for (FormArea excludeArea : areaExcludes.values()) {
				if(area.equals(excludeArea)) {
					return true;
				}
			}
		}		
		return false;
	}
}
