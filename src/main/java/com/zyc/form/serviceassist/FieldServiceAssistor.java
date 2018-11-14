package com.zyc.form.serviceassist;

import java.util.List;

import com.zyc.baselibs.data.EmptyNodeType;
import com.zyc.baselibs.ex.BussinessException;
import com.zyc.baselibs.vo.Pagination;
import com.zyc.baselibs.web.bootstrap.BsTableDataSource;
import com.zyc.baselibs.web.bootstrap.TreeViewNode;
import com.zyc.form.vo.FormFieldVO;

public interface FieldServiceAssistor {
	
	/**
	 * 整合一个树形结构的表单数据源
	 * @return
	 */
	List<TreeViewNode> composeAreaFieldTree(String formid, EmptyNodeType emptyNodeType) throws BussinessException;

	BsTableDataSource<FormFieldVO> composeFormFieldBsTableDataSource(FormFieldVO condition, String searchText, Pagination pagination);

}
