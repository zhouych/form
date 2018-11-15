package com.zyc.form.entities;

import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.EnumMapping;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.entities.DescriptionBaseEntity;
import com.zyc.form.data.FieldDataType;
import com.zyc.form.data.FieldDisplayType;
import com.zyc.form.data.FormArea;

public abstract class AbstractField extends DescriptionBaseEntity implements AbstractFieldLabelable {

	public static final String FIELD_FORMAREA = "formarea";
	public static final String FIELD_FIELDVALUE = "fieldvalue";

	@FieldRule(required = true, externalUneditable = true)
	@EnumMapping(enumClazz = FormArea.class)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 16)
	private String formarea;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.BOOLEAN)
	private Boolean sysfield;
	
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

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 1024)
	private String expression;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 2048)
	private String expressiontext;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String expressiondefault;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String expressiondefaulttext;

	@FieldRule(required = true)
	@DatabaseColumn(jdbcType = JDBCType.BOOLEAN)
	private Boolean editable;

	public String getFormarea() {
		return formarea;
	}

	public void setFormarea(String formarea) {
		this.formarea = formarea;
	}

	public Boolean getSysfield() {
		return sysfield;
	}

	public void setSysfield(Boolean sysfield) {
		this.sysfield = sysfield;
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

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getExpressiontext() {
		return expressiontext;
	}

	public void setExpressiontext(String expressiontext) {
		this.expressiontext = expressiontext;
	}

	public String getExpressiondefault() {
		return expressiondefault;
	}

	public void setExpressiondefault(String expressiondefault) {
		this.expressiondefault = expressiondefault;
	}

	public String getExpressiondefaulttext() {
		return expressiondefaulttext;
	}

	public void setExpressiondefaulttext(String expressiondefaulttext) {
		this.expressiondefaulttext = expressiondefaulttext;
	}

	public Boolean getEditable() {
		return editable;
	}
	
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	
	@Override
	public AbstractField clean() {
		super.clean();
		this.formarea = null;
		this.sysfield = null;
		this.fieldvalue = null;
		this.fieldname = null;
		this.datatype = null;
		this.displaytype = null;
		this.expression = null;
		this.expressiontext = null;
		this.editable = null;
		return this;
	}
	
	@Override
	public void init() {
		super.init();
		this.sysfield = StringUtils.isBlank(this.fieldvalue) || getItemIndex(this.fieldvalue) > 0;
	}

	@Override
	public String getDatatypelabel() {
		return this.getDatatype() == null ? null : Enum.valueOf(FieldDataType.class, this.getDatatype().toUpperCase()).getText();
	}
	
	@Override
	public String getDisplaytypelabel() {
		return this.getDisplaytype() == null ? null :Enum.valueOf(FieldDisplayType.class, this.getDisplaytype().toUpperCase()).getText();
	}
	
	@Override
	public String getFormarealabel() {
		return this.getFormarea() == null ? null : Enum.valueOf(FormArea.class, this.getFormarea().toUpperCase()).getText();
	}
	
	@Override
	public String getEditablelabel() {
		return this.getEditable() != null && this.getEditable() ? "是" : "否";
	}
	
	/**
	 * 获取自定义字段的序号
	 * @param itemfieldvalue 自定义字段
	 * @return
	 */
	public static int getItemIndex(String itemfieldvalue) {
		String temp = itemfieldvalue.replace("item0", "").replace("item", "");
		return StringUtils.isNumeric(temp) ? Integer.valueOf(temp) : -1;
	}
}
