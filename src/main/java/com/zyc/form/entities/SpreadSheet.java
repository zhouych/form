package com.zyc.form.entities;

import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.entities.DescriptionBaseEntity;

@DatabaseTable(name = "spreadsheets")
public class SpreadSheet extends DescriptionBaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -7940023106871703835L;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String spreadid;

	@FieldRule(required = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 64)
	private String name;

	public String getSpreadid() {
		return spreadid;
	}

	public void setSpreadid(String spreadid) {
		this.spreadid = spreadid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public SpreadSheet clean() {
		super.clean();
		this.name = null;
		return this;
	}
	
}
