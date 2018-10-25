package com.zyc.form.serviceassist;

import java.util.List;

import com.zyc.baselibs.ex.BussinessException;
import com.zyc.baselibs.web.EmptyNodeType;
import com.zyc.baselibs.web.bootstrap.TreeViewNode;

public interface AreaFieldServiceAssistor {
	
	/**
	 * 整合一个树形结构的表单数据源
	 * @return
	 */
	List<TreeViewNode> composeAreaFieldTree(String formid, EmptyNodeType emptyNodeType) throws BussinessException;

}
