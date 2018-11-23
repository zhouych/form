package com.zyc.form.entities;

import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.entities.DescriptionBaseEntity;

@DatabaseTable(name = "spreads")
public class Fspread extends DescriptionBaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1752297332060772330L;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String formid;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.CLOB, virtualType = "Clob")
	private String json;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.CLOB, virtualType = "Clob")
	private String xml;

	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}
	
	@Override
	public Fspread clean() {
		super.clean();
		this.formid = null;
		this.json = null;
		this.xml = null;
		return this;
	}
}
