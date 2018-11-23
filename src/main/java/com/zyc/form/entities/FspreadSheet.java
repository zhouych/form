package com.zyc.form.entities;

import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.entities.DescriptionBaseEntity;

@DatabaseTable(name = "spreadsheets")
public class FspreadSheet extends DescriptionBaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -7940023106871703835L;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String fspreadid;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.INTEGER)
	private Integer index;
	
	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 64)
	private String name;

	public String getFspreadid() {
		return fspreadid;
	}

	public void setFspreadid(String fspreadid) {
		this.fspreadid = fspreadid;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public FspreadSheet clean() {
		super.clean();
		this.name = null;
		return this;
	}
	
}
