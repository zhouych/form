package com.zyc.form.vo;

import org.springframework.beans.BeanUtils;

import com.zyc.baselibs.aopv.Verifiable;
import com.zyc.baselibs.entities.EntityCopyable;
import com.zyc.form.entities.Form;

@Verifiable
public class FormVO extends Form implements EntityCopyable<Form> {

	private static final long serialVersionUID = -1969081470802999933L;
	
	private String formdomainname;
	
	public FormVO() {
		
	}

	public FormVO(Form form) {
		BeanUtils.copyProperties(form, this);
	}
	
	public String getFormdomainname() {
		return formdomainname;
	}

	public void setFormdomainname(String formdomainname) {
		this.formdomainname = formdomainname;
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
