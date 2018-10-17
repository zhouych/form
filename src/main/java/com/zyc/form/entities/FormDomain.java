package com.zyc.form.entities;

import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.entities.DescriptionBaseEntity;
import com.zyc.baselibs.entities.Labelable;

/**
 * 表单域
 * @author zhouyancheng
 *
 */
@DatabaseTable(name = "formdomains")
public class FormDomain extends DescriptionBaseEntity implements java.io.Serializable, Labelable {

	private static final long serialVersionUID = -6163333646343142660L;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 64)
	private String domainname;
	
	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 32)
	private String domaincode;
	
	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.BOOLEAN)
	private Boolean enabledbudgetctrl;

	public String getDomainname() {
		return domainname;
	}

	public void setDomainname(String domainname) {
		this.domainname = domainname;
	}

	public String getDomaincode() {
		return domaincode;
	}

	public void setDomaincode(String domaincode) {
		this.domaincode = domaincode;
	}

	public Boolean getEnabledbudgetctrl() {
		return enabledbudgetctrl;
	}

	public void setEnabledbudgetctrl(Boolean enabledbudgetctrl) {
		this.enabledbudgetctrl = enabledbudgetctrl;
	}
	
	@Override
	public FormDomain clean() {
		super.clean();
		this.domainname = null;
		this.domaincode = null;
		this.enabledbudgetctrl = null;
		return this;
	}

	@Override
	public String label() {
		return this.getDomaincode() + " - " + this.getDomainname();
	}
}
