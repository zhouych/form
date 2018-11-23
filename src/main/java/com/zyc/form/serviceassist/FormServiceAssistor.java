package com.zyc.form.serviceassist;

import java.util.List;

import com.zyc.baselibs.data.EmptyNodeType;
import com.zyc.baselibs.vo.EntryBean;
import com.zyc.baselibs.vo.Pagination;
import com.zyc.baselibs.web.bootstrap.BsTableDataSource;
import com.zyc.baselibs.web.bootstrap.HierarchySelectNode;
import com.zyc.form.vo.FormVO;

public interface FormServiceAssistor {
	
	/**
	 * 整合一个横向递进结构的表单数据源
	 * @return
	 */
	List<HierarchySelectNode> composeFormTree(EmptyNodeType emptyNodeType);

	/**
	 * 合成bootstrap-table表格插件的数据源
	 * @param condition 过滤条件
	 * @param searchText 过滤关键字
	 * @param pagination 分页参数
	 * @return
	 */
	BsTableDataSource<FormVO> composeBsTableDataSource(FormVO condition, String searchText, Pagination pagination);

	List<EntryBean> formAreas(String formid, EmptyNodeType empty);
}
