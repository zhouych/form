package com.zyc.form.serviceassist;

import java.util.List;

import com.zyc.baselibs.web.EmptyNodeType;
import com.zyc.baselibs.web.bootstrap.HierarchySelectNode;

public interface FormServiceAssistor {
	
	/**
	 * 整合一个横向递进结构的表单数据源
	 * @return
	 */
	List<HierarchySelectNode> composeFormTree(EmptyNodeType emptyNodeType);
}
