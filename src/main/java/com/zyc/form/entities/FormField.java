package com.zyc.form.entities;

import java.lang.reflect.Field;
import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.entities.BaseEntity;
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
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String formid;
	
	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String metafieldid;
	
	public FormField() {
		
	}
	
	public FormField(MetaField mf) {
		List<String> ignores = new ArrayList<String>();
		ReflectUtils.scanFields(BaseEntity.class, new Visitor<Field, Boolean>() {
			@Override
			public Boolean visit(Field field) {
				ignores.add(field.getName());
				return false;
			}
		}, false, ReflectUtils.MODIFIER_STATIC$FINAL);
		
		BeanUtils.copyProperties(mf, this, ignores.toArray(new String[ignores.size()]));
	}
	 
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
	
	@Override
	public FormField clean() {
		super.clean();
		this.formid = null;
		this.metafieldid = null;
		return this;
	}
}
