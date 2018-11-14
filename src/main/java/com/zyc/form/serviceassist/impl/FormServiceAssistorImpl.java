package com.zyc.form.serviceassist.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyc.baselibs.commons.CollectionUtils;
import com.zyc.baselibs.data.EmptyNodeType;
import com.zyc.baselibs.vo.Pagination;
import com.zyc.baselibs.vo.PaginationResult;
import com.zyc.baselibs.web.bootstrap.BsTableDataSource;
import com.zyc.baselibs.web.bootstrap.HierarchySelectNode;
import com.zyc.form.service.FormDomainService;
import com.zyc.form.service.FormService;
import com.zyc.form.serviceassist.FormServiceAssistor;
import com.zyc.form.vo.FormDomainVO;
import com.zyc.form.vo.FormVO;

@Service
public class FormServiceAssistorImpl implements FormServiceAssistor {

	@Autowired
	private FormDomainService domainService;
	
	@Autowired
	private FormService formService; 
	
	@Override
	public List<HierarchySelectNode> composeFormTree(EmptyNodeType emptyNodeType) {
		List<FormDomainVO> domains = this.domainService.selectAll();
		if(!CollectionUtils.hasElement(domains)) {
			return null;
		}
		
		List<FormVO> forms = this.formService.selectAll();
		List<FormVO> removes = new ArrayList<FormVO>();

		List<HierarchySelectNode> data = new ArrayList<HierarchySelectNode>();
		for (FormDomainVO domain : domains) {
			data.add(new HierarchySelectNode(domain.getId(), 1, domain.label()));
			if(CollectionUtils.hasElement(forms)) {
				for (FormVO form : forms) {
					if(domain.getId().equals(form.getFormdomainid())) {
						data.add(new HierarchySelectNode(form.getId(), 2, form.label()));
						removes.add(form);
					}
				}
				
				if(!removes.isEmpty()) {
					forms.removeAll(removes);
					removes.clear();
				}
			}
		}
		
		if(emptyNodeType != null) {
			data.add(0, new HierarchySelectNode(emptyNodeType));
		}
		
		return data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BsTableDataSource<FormVO> composeBsTableDataSource(FormVO condition, String searchText, Pagination pagination) {
		PaginationResult<FormVO> result = this.formService.selectByPage(condition, searchText, pagination);
		BsTableDataSource<FormVO> dataSource = new BsTableDataSource<FormVO>();
		dataSource.setRows(result.getRows());
		dataSource.setTotal(result.getTotal());
		return dataSource;
	}

}
