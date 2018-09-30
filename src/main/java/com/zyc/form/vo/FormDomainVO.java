package com.zyc.form.vo;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.zyc.baselibs.aopv.Verifiable;
import com.zyc.form.entities.CtrlDimSource;
import com.zyc.form.entities.FormDomain;

@Verifiable
public class FormDomainVO extends FormDomain {
	
	private static final long serialVersionUID = -7241150664326724541L;
	
	private List<CtrlDimSource> ctrlDimSources;

	public FormDomainVO() {
		
	}
	
	public FormDomainVO(FormDomain formDomain) {
		BeanUtils.copyProperties(formDomain, this);
	}
	
	public List<CtrlDimSource> getCtrlDimSources() {
		return ctrlDimSources;
	}

	public void setCtrlDimSources(List<CtrlDimSource> ctrlDimSources) {
		this.ctrlDimSources = ctrlDimSources;
	}
}
