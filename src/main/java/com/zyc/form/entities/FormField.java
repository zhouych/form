package com.zyc.form.entities;

import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.entities.Labelable;

/**
 * 表单字段
 * @author zhouyancheng
 *
 */
@DatabaseTable(name = "formfields")
public class FormField extends AbstractField implements java.io.Serializable, Labelable {
	
	private static final long serialVersionUID = -433929870224425673L;

	@FieldRule(required = true, externalUneditable = true)
	private String formid;
	
	@FieldRule(required = true, externalUneditable = true)
	private String metafieldid;
	 
	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	public String getMetafieldid() {
		return metafieldid;
	}

	public void setMetafieldid(String metafieldid) {
		this.metafieldid = metafieldid;
	}

	@Override
	public String label() {
		return this.getFieldvalue() + " - " + this.getFieldname();
	}
}
