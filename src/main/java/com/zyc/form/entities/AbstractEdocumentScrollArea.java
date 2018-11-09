package com.zyc.form.entities;

import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.commons.StringUtils;

public abstract class AbstractEdocumentScrollArea<T extends AbstractEdocumentScrollArea<?>> extends AbstractEdocumentItem<T> {

	private static final long serialVersionUID = 258499219665746057L;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String edocumentid;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.INTEGER)
	private Integer index;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String title;

	public String getEdocumentid() {
		return edocumentid;
	}

	public void setEdocumentid(String edocumentid) {
		this.edocumentid = edocumentid;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean businessEquals(T obj) {
		if(obj == null) {
			return false;
		}
		return StringUtils.equals(this.getId(), obj.getId()) 
				&& StringUtils.equals(this.getEdocumentid(), this.getEdocumentid());
	}

	@Override
	public String label() {
		return this.getTitle();
	}
}
