package com.zyc.form.vo;

import org.springframework.beans.BeanUtils;

import com.zyc.baselibs.entities.Entityable;
import com.zyc.form.entities.FormDomain;

public class FormDomainQueryVO extends FormDomain implements Entityable<FormDomain> {

	private static final long serialVersionUID = 13928768473286691L;

	@Override
	public FormDomain toEntity() {
		FormDomain domain = new FormDomain();
		BeanUtils.copyProperties(this, domain);
		return domain;
	}
	
}
