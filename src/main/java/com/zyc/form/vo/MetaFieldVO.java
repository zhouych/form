package com.zyc.form.vo;

import org.springframework.beans.BeanUtils;

import com.zyc.form.entities.MetaField;

public class MetaFieldVO extends MetaField {

	private static final long serialVersionUID = -4587114845739024653L;

	public MetaFieldVO() {
		
	}
	
	public MetaFieldVO(MetaField metaField) {
		BeanUtils.copyProperties(metaField, this);
	}
}
