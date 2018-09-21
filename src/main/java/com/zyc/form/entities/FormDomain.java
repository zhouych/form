package com.zyc.form.entities;

import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.entities.BaseEntity;

/**
 * 表单域
 * @author zhouyancheng
 *
 */
@DatabaseTable(name = "formdomains")
public class FormDomain extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -6163333646343142660L;

	@FieldRule(required = true, externalUneditable = false)
	private String domainname;
	@FieldRule(required = true, externalUneditable = true)
	private String domaincode;
	@FieldRule(required = true, externalUneditable = true)
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
		return this;
	}
}
