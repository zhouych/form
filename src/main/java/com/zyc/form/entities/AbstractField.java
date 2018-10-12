package com.zyc.form.entities;

import com.zyc.baselibs.annotation.EnumMapping;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.form.data.FormArea;

public abstract class AbstractField extends  BaseEntity {

	@FieldRule(required = true, externalUneditable = true)
	@EnumMapping(enumClazz = FormArea.class)
	private String formarea;
	
	@FieldRule(required = true, externalUneditable = true)
	private String fieldvalue;
	
	@FieldRule(required = true)
	private String fieldname;

	@FieldRule(required = true)
	private String datatype;

	@FieldRule(required = true)
	private String displaytype;

	@FieldRule(required = true)
	private boolean editable;

	public String getFormarea() {
		return formarea;
	}

	public void setFormarea(String formarea) {
		this.formarea = formarea;
	}

	public String getFieldvalue() {
		return fieldvalue;
	}

	public void setFieldvalue(String fieldvalue) {
		this.fieldvalue = fieldvalue;
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getDisplaytype() {
		return displaytype;
	}

	public void setDisplaytype(String displaytype) {
		this.displaytype = displaytype;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
