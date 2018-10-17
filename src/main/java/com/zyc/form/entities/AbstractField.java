package com.zyc.form.entities;

import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.EnumMapping;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.entities.DescriptionBaseEntity;
import com.zyc.form.data.FieldDataType;
import com.zyc.form.data.FieldDisplayType;
import com.zyc.form.data.FormArea;

public abstract class AbstractField extends DescriptionBaseEntity {

	@FieldRule(required = true, externalUneditable = true)
	@EnumMapping(enumClazz = FormArea.class)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 16)
	private String formarea;
	
	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 32)
	private String fieldvalue;
	
	@FieldRule(required = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 32)
	private String fieldname;

	@FieldRule(required = true)
	@EnumMapping(enumClazz = FieldDataType.class)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 16)
	private String datatype;

	@FieldRule(required = true)
	@EnumMapping(enumClazz = FieldDisplayType.class)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 16)
	private String displaytype;

	@FieldRule(required = true)
	@DatabaseColumn(jdbcType = JDBCType.BOOLEAN)
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
