package com.zyc.form.entities;

import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.entities.BaseEntity;

@DatabaseTable(name = "spreadsheetareas")
public class SpreadSheetArea extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -7940023106871703835L;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String spreadsheetid;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.INTEGER)
	private Integer startrow;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.INTEGER)
	private Integer startcol;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.INTEGER)
	private Integer endrow;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.INTEGER)
	private Integer endcol;

	public String getSpreadsheetid() {
		return spreadsheetid;
	}

	public void setSpreadsheetid(String spreadsheetid) {
		this.spreadsheetid = spreadsheetid;
	}

	public Integer getStartrow() {
		return startrow;
	}

	public void setStartrow(Integer startrow) {
		this.startrow = startrow;
	}

	public Integer getStartcol() {
		return startcol;
	}

	public void setStartcol(Integer startcol) {
		this.startcol = startcol;
	}

	public Integer getEndrow() {
		return endrow;
	}

	public void setEndrow(Integer endrow) {
		this.endrow = endrow;
	}

	public Integer getEndcol() {
		return endcol;
	}

	public void setEndcol(Integer endcol) {
		this.endcol = endcol;
	}
}
