package com.zyc.form.webadvisory;

import java.util.List;

import com.zyc.baselibs.web.bootstrap.EmptyNodeType;
import com.zyc.baselibs.web.bootstrap.HierarchySelectNode;

public interface FormIndustryAdvisor {
	
	/**
	 * 整合一个树形结构的表单数据源
	 * @return
	 */
	List<HierarchySelectNode> composeFormTree(EmptyNodeType emptyNodeType);
}
