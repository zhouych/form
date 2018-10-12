package com.zyc.form.vo;

import org.springframework.beans.BeanUtils;

import com.zyc.form.entities.FormField;

public class FormFieldVO extends FormField {

	private static final long serialVersionUID = 1965252796557575249L;
	
	public FormFieldVO() {
		
	}
	
	public FormFieldVO(FormField formField) {
		BeanUtils.copyProperties(formField, this);
	}

}
