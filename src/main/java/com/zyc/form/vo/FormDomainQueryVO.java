package com.zyc.form.vo;

import org.springframework.beans.BeanUtils;

import com.zyc.baselibs.entities.EntityCopyable;
import com.zyc.form.entities.FormDomain;

public class FormDomainQueryVO extends FormDomain implements EntityCopyable<FormDomain> {

	private static final long serialVersionUID = 13928768473286691L;

	@Override
	public FormDomain copyEntity() {
		FormDomain domain = new FormDomain();
		BeanUtils.copyProperties(this, domain);
		return domain;
	}
	
}
