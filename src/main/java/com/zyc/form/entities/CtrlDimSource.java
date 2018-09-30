package com.zyc.form.entities;

import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.aopv.Verifiable;
import com.zyc.baselibs.entities.BaseEntity;

/**
 * 表单域的控制维度数据源
 * @author zhouyancheng
 *
 */
@Verifiable
@DatabaseTable(name = "ctrldimsources")
public class CtrlDimSource extends BaseEntity {

	@FieldRule(required = true, externalUneditable = true)
	private String formdomainid;
	@FieldRule(required = true, externalUneditable = false)
	private String dimid;
	@FieldRule(required = true, externalUneditable = false)
	private String dimname;
	@FieldRule(required = true, externalUneditable = false)
	private String dimcode;
	@FieldRule(required = true, externalUneditable = false)
	private String expression;
	@FieldRule(required = true, externalUneditable = false)
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
}
