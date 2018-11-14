package com.zyc.form.serviceassist;

import java.util.List;

import com.zyc.baselibs.data.EmptyNodeType;
import com.zyc.baselibs.vo.EntryBean;
import com.zyc.baselibs.web.bootstrap.HierarchySelectNode;

public interface FormDomainServiceAssistor {
	
	/**
	 * 整合一个横向递进结构的表单域数据源
	 * @return
	 */
	List<HierarchySelectNode> composeDomains(EmptyNodeType emptyNodeType);
	
	List<EntryBean> allDomainEntryBeans(EmptyNodeType emptyNodeType);
}
