package com.zyc.form.vo;

import org.springframework.beans.BeanUtils;

import com.zyc.form.entities.Form;

public class FormVO extends Form {

	private static final long serialVersionUID = -1969081470802999933L;

	public FormVO() {
		
	}
	
	public FormVO(Form form) {
		BeanUtils.copyProperties(form, this);
	}
}
