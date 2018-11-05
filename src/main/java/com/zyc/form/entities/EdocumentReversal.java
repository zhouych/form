package com.zyc.form.entities;

import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.FieldRule;

@DatabaseTable(name = "edocumentreversals")
public class EdocumentReversal extends AbstractEdocumentMoneyArea<EdocumentReversal> {

	private static final long serialVersionUID = -8636296549042496703L;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 64)
	private String edocumentnumber;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String edocumentdetailid;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.DOUBLE)
	private Double edocumentdetailnaturalmoney;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.DOUBLE)
	private Double edocumentdetailavailablemoney;

	public String getEdocumentnumber() {
		return edocumentnumber;
	}

	public void setEdocumentnumber(String edocumentnumber) {
		this.edocumentnumber = edocumentnumber;
	}

	public String getEdocumentdetailid() {
		return edocumentdetailid;
	}

	public void setEdocumentdetailid(String edocumentdetailid) {
		this.edocumentdetailid = edocumentdetailid;
	}

	public Double getEdocumentdetailnaturalmoney() {
		return edocumentdetailnaturalmoney;
	}

	public void setEdocumentdetailnaturalmoney(Double edocumentdetailnaturalmoney) {
		this.edocumentdetailnaturalmoney = edocumentdetailnaturalmoney;
	}

	public Double getEdocumentdetailavailablemoney() {
		return edocumentdetailavailablemoney;
	}

	public void setEdocumentdetailavailablemoney(Double edocumentdetailavailablemoney) {
		this.edocumentdetailavailablemoney = edocumentdetailavailablemoney;
	}
}
