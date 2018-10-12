package com.zyc.form.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.zyc.baselibs.vo.EntryBean;

public enum FormType {
	
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
	
	public static FormType from(String value) {
		return Enum.valueOf(FormType.class, value.toUpperCase());
	}
}
