package com.zyc.form.entities;

import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.FieldRule;

public abstract class AbstractEdocumentMoneyArea<T extends AbstractEdocumentMoneyArea<?>> extends AbstractEdocumentScrollArea<T> {

	private static final long serialVersionUID = 4816228619869478223L;
	
	@FieldRule(required = false, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.DOUBLE)
	private Double naturalmoney;

	public Double getNaturalmoney() {
		return naturalmoney;
	}

	public void setNaturalmoney(Double naturalmoney) {
		this.naturalmoney = naturalmoney;
	}
}
