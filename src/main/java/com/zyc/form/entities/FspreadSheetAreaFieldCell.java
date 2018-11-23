package com.zyc.form.entities;

import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.entities.BaseEntity;

@DatabaseTable(name = "spreadsheetareafieldcells")
public class FspreadSheetAreaFieldCell extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -7940023106871703835L;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String fspreadsheetareaid;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.INTEGER)
	private Integer row;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.INTEGER)
	private Integer col;

	@FieldRule(required = true, externalUneditable = false)
	private String area;

	@FieldRule(required = true, externalUneditable = false)
	private String field;

	public String getFspreadsheetareaid() {
		return fspreadsheetareaid;
	}

	public void setFspreadsheetareaid(String fspreadsheetareaid) {
		this.fspreadsheetareaid = fspreadsheetareaid;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getCol() {
		return col;
	}

	public void setCol(Integer col) {
		this.col = col;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
