package com.zyc.form.entities;

import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.aopv.Verifiable;
import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.baselibs.entities.Businessable;

/**
 * 表单域的控制维度数据源
 * @author zhouyancheng
 *
 */
@Verifiable
@DatabaseTable(name = "ctrldimsources")
public class CtrlDimSource extends BaseEntity implements Businessable<CtrlDimSource> {

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String formdomainid;
	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String dimid;
	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 64)
	private String dimname;
	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 64)
	private String dimcode;
	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 1024)
	private String expression;
	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 2048)
	private String expressiontext;
	
	public String getFormdomainid() {
		return formdomainid;
	}
	public void setFormdomainid(String formdomainid) {
		this.formdomainid = formdomainid;
	}
	public String getDimid() {
		return dimid;
	}
	public void setDimid(String dimid) {
		this.dimid = dimid;
	}
	public String getDimname() {
		return dimname;
	}
	public void setDimname(String dimname) {
		this.dimname = dimname;
	}
	public String getDimcode() {
		return dimcode;
	}
	public void setDimcode(String dimcode) {
		this.dimcode = dimcode;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getExpressiontext() {
		return expressiontext;
	}
	public void setExpressiontext(String expressiontext) {
		this.expressiontext = expressiontext;
	}
	
	@Override
	public CtrlDimSource clean() {
		super.clean();
		this.formdomainid = null;
		this.dimid = null;
		this.dimname = null;
		this.dimcode = null;
		this.expression = null;
		this.expressiontext = null;
		return this;
	}
	
	@Override
	public boolean businessEquals(CtrlDimSource obj) {
		if(obj == null || this.getFormdomainid() == null || this.getDimid() == null || this.getDimcode() == null) {
			return false;
		}
		
		//同一表单下，两个控制维度数据源各自的维度id、维度编码都相等，则业务上它们是相等的。
		return this.getFormdomainid().equals(obj.getFormdomainid()) && this.getDimid().equals(obj.getDimid()) && this.getDimcode().equals(obj.getDimcode());
	}
}
