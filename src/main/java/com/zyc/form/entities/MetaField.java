package com.zyc.form.entities;

import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.entities.Labelable;

/**
 * 元字段
 * @author zhouyancheng
 *
 */
@DatabaseTable(name = "metafields")
public class MetaField extends AbstractField implements java.io.Serializable, Labelable {

	private static final long serialVersionUID = 8335774671760974665L;

	@Override
	public String label() {
		return this.getFieldvalue() + " - " + this.getFieldname();
	}
	
	@Override
	public MetaField clean() {
		// TODO Auto-generated method stub
		super.clean();
		
		return this;
	}
}
