package com.zyc.form.entities;

import java.math.BigDecimal;
import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.FieldRule;

@DatabaseTable(name = "edocumentdetails")
public class EdocumentDetail extends AbstractEdocumentMoneyArea<EdocumentDetail> {

	private static final long serialVersionUID = 8745083676557134372L;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String dimorganization;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String dimorganizationlabel;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String dimexpenseaccount;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String dimexpenseaccountlabel;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String dimproject;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String dimprojectlabel;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String dimyear;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String dimyearlabel;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String dim01;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String dim01label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String dim02;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String dim02label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String dim03;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String dim03label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String dim04;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String dim04label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String dim05;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String dim05label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String originalcurrency;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String originalcurrencylabel;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String naturalcurrency;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String naturalcurrencylabel;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.DECIMAL)
	private BigDecimal exchangerate;
	
	@FieldRule(required = false, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.DOUBLE)
	private Double originalmoney;

	public String getDimorganization() {
		return dimorganization;
	}

	public void setDimorganization(String dimorganization) {
		this.dimorganization = dimorganization;
	}

	public String getDimorganizationlabel() {
		return dimorganizationlabel;
	}

	public void setDimorganizationlabel(String dimorganizationlabel) {
		this.dimorganizationlabel = dimorganizationlabel;
	}

	public String getDimexpenseaccount() {
		return dimexpenseaccount;
	}

	public void setDimexpenseaccount(String dimexpenseaccount) {
		this.dimexpenseaccount = dimexpenseaccount;
	}

	public String getDimexpenseaccountlabel() {
		return dimexpenseaccountlabel;
	}

	public void setDimexpenseaccountlabel(String dimexpenseaccountlabel) {
		this.dimexpenseaccountlabel = dimexpenseaccountlabel;
	}

	public String getDimproject() {
		return dimproject;
	}

	public void setDimproject(String dimproject) {
		this.dimproject = dimproject;
	}

	public String getDimprojectlabel() {
		return dimprojectlabel;
	}

	public void setDimprojectlabel(String dimprojectlabel) {
		this.dimprojectlabel = dimprojectlabel;
	}

	public String getDimyear() {
		return dimyear;
	}

	public void setDimyear(String dimyear) {
		this.dimyear = dimyear;
	}

	public String getDimyearlabel() {
		return dimyearlabel;
	}

	public void setDimyearlabel(String dimyearlabel) {
		this.dimyearlabel = dimyearlabel;
	}

	public String getDim01() {
		return dim01;
	}

	public void setDim01(String dim01) {
		this.dim01 = dim01;
	}

	public String getDim01label() {
		return dim01label;
	}

	public void setDim01label(String dim01label) {
		this.dim01label = dim01label;
	}

	public String getDim02() {
		return dim02;
	}

	public void setDim02(String dim02) {
		this.dim02 = dim02;
	}

	public String getDim02label() {
		return dim02label;
	}

	public void setDim02label(String dim02label) {
		this.dim02label = dim02label;
	}

	public String getDim03() {
		return dim03;
	}

	public void setDim03(String dim03) {
		this.dim03 = dim03;
	}

	public String getDim03label() {
		return dim03label;
	}

	public void setDim03label(String dim03label) {
		this.dim03label = dim03label;
	}

	public String getDim04() {
		return dim04;
	}

	public void setDim04(String dim04) {
		this.dim04 = dim04;
	}

	public String getDim04label() {
		return dim04label;
	}

	public void setDim04label(String dim04label) {
		this.dim04label = dim04label;
	}

	public String getDim05() {
		return dim05;
	}

	public void setDim05(String dim05) {
		this.dim05 = dim05;
	}

	public String getDim05label() {
		return dim05label;
	}

	public void setDim05label(String dim05label) {
		this.dim05label = dim05label;
	}

	public String getOriginalcurrency() {
		return originalcurrency;
	}

	public void setOriginalcurrency(String originalcurrency) {
		this.originalcurrency = originalcurrency;
	}

	public String getOriginalcurrencylabel() {
		return originalcurrencylabel;
	}

	public void setOriginalcurrencylabel(String originalcurrencylabel) {
		this.originalcurrencylabel = originalcurrencylabel;
	}

	public String getNaturalcurrency() {
		return naturalcurrency;
	}

	public void setNaturalcurrency(String naturalcurrency) {
		this.naturalcurrency = naturalcurrency;
	}

	public String getNaturalcurrencylabel() {
		return naturalcurrencylabel;
	}

	public void setNaturalcurrencylabel(String naturalcurrencylabel) {
		this.naturalcurrencylabel = naturalcurrencylabel;
	}

	public BigDecimal getExchangerate() {
		return exchangerate;
	}

	public void setExchangerate(BigDecimal exchangerate) {
		this.exchangerate = exchangerate;
	}

	public Double getOriginalmoney() {
		return originalmoney;
	}

	public void setOriginalmoney(Double originalmoney) {
		this.originalmoney = originalmoney;
	}
}
