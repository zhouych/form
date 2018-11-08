package com.zyc.form.vo;

import org.springframework.beans.BeanUtils;

import com.zyc.baselibs.aopv.Verifiable;
import com.zyc.baselibs.entities.EntityCopyable;
import com.zyc.form.entities.FormField;

@Verifiable
public class FormFieldVO extends FormField implements EntityCopyable<FormField> {

	private static final long serialVersionUID = 1965252796557575249L;

	private String formname;
	
	public FormFieldVO() {
		
	}

	public FormFieldVO(FormField formField) {
		BeanUtils.copyProperties(formField, this);
	}
	
	public String getFormname() {
		return formname;
	}

	public void setFormname(String formname) {
		this.formname = formname;
	}

	@Override
	public FormField copyEntity() {
		FormField form = new FormField();
		BeanUtils.copyProperties(this, form);
		return form;
	}

	public static FormFieldVO newInstance() {
		FormFieldVO vo = new FormFieldVO();
		vo.init();
		vo.createIdWhenNot();
		return vo;
	}

}
