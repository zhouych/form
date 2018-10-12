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
		// TODO Auto-generated method stub
		return null;
	}
}
