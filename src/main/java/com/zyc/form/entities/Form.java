package com.zyc.form.entities;

import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.EnumMapping;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.baselibs.entities.Labelable;
import com.zyc.form.data.FormType;

/**
 * 表单
 * @author zhouyancheng
 *
 */
@DatabaseTable(name = "forms")
public class Form extends BaseEntity implements java.io.Serializable, Labelable {

	private static final long serialVersionUID = -1527160855494247508L;

	@FieldRule(required = true, externalUneditable = true)
	private String formdomainid;
	
	@FieldRule(required = true)
	private String formname;
	
	@FieldRule(required = true, externalUneditable = true)
	private String formcode;

	@FieldRule(required = true, externalUneditable = true)
	@EnumMapping(enumClazz = FormType.class)
	private String formtype;

	public String getFormdomainid() {
		return formdomainid;
	}

	public void setFormdomainid(String formdomainid) {
		this.formdomainid = formdomainid;
	}

	public String getFormname() {
		return formname;
	}

	public void setFormname(String formname) {
		this.formname = formname;
	}

	public String getFormcode() {
		return formcode;
	}

	public void setFormcode(String formcode) {
		this.formcode = formcode;
	}

	public String getFormtype() {
		return formtype;
	}

	public void setFormtype(String formtype) {
		this.formtype = formtype;
	}

	@Override
	public String label() {
		return this.getFormcode() + " - " + this.getFormname();
	}
}
