package com.zyc.form.entities;

import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.EnumMapping;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.entities.Businessable;
import com.zyc.baselibs.entities.DescriptionBaseEntity;
import com.zyc.baselibs.entities.Labelable;
import com.zyc.form.data.FormType;

/**
 * 表单
 * @author zhouyancheng
 *
 */
@DatabaseTable(name = "forms")
public class Form extends DescriptionBaseEntity implements java.io.Serializable, Labelable, Businessable<Form> {

	private static final long serialVersionUID = -1527160855494247508L;
	
	public static final String FIELD_FORMDOMAINID = "formdomainid";
	public static final String FIELD_FORMTYPE = "formtype";
	public static final String FIELD_FORMCODE = "formcode";

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String formdomainid;

	@FieldRule(required = true, externalUneditable = true)
	@EnumMapping(enumClazz = FormType.class)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 16)
	private String formtype;
	
	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 32)
	private String formcode;
	
	@FieldRule(required = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 64)
	private String formname;

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
	public Form clean() {
		super.clean();
		this.formdomainid = null;
		this.formname = null;
		this.formcode = null;
		this.formtype = null;
		return this;
	}

	@Override
	public String label() {
		return this.getFormcode() + " - " + this.getFormname();
	}

	@Override
	public boolean businessEquals(Form obj) {
		if(obj == null) {
			return false;
		}
		
		return  StringUtils.equals(this.getFormdomainid(), obj.getFormdomainid())
				&& StringUtils.equals(this.getId(), obj.getId())
				&& StringUtils.equals(this.getFormcode(), obj.getFormcode());
	}
}
