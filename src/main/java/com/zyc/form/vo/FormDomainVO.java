package com.zyc.form.vo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.form.entities.CtrlDimSource;
import com.zyc.form.entities.FormDomain;

public class FormDomainVO extends FormDomain {
	
	private static final long serialVersionUID = -7241150664326724541L;
	
	private List<CtrlDimSource> ctrlDimSources;

	public FormDomainVO() {
		
	}
	
	public FormDomainVO(FormDomain formDomain) {
		FormDomainVO that = this;
		Class<?> thatClazz = that.getClass();
		ReflectUtils.scanFields(FormDomain.class, new Visitor<Field, Boolean>() {
			@Override
			public Boolean visit(Field field) {
				try {
					thatClazz.getDeclaredField(field.getName()).set(that, field.get(formDomain));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}
		}, false, new int[] { Modifier.STATIC, Modifier.FINAL });
	}
	
	public List<CtrlDimSource> getCtrlDimSources() {
		return ctrlDimSources;
	}

	public void setCtrlDimSources(List<CtrlDimSource> ctrlDimSources) {
		this.ctrlDimSources = ctrlDimSources;
	}
}
