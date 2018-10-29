package com.zyc.form.vo;

import org.springframework.beans.BeanUtils;

import com.zyc.baselibs.entities.EntityCopyable;
import com.zyc.form.entities.Form;

public class FormVO extends Form implements EntityCopyable<Form> {

	private static final long serialVersionUID = -1969081470802999933L;

	public FormVO() {
		
	}
	
	public FormVO(Form form) {
		BeanUtils.copyProperties(form, this);
	}

	@Override
	public Form copyEntity() {
		Form form = new Form();
		BeanUtils.copyProperties(this, form);
		return form;
	}

	public static FormVO newInstance() {
		FormVO vo = new FormVO();
		vo.init();
		vo.createIdWhenNot();
		return vo;
	}
}
