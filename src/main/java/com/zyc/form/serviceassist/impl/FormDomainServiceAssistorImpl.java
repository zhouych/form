package com.zyc.form.serviceassist.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyc.baselibs.commons.CollectionUtils;
import com.zyc.baselibs.data.EmptyNodeType;
import com.zyc.baselibs.vo.EntryBean;
import com.zyc.baselibs.web.bootstrap.HierarchySelectNode;
import com.zyc.form.service.FormDomainService;
import com.zyc.form.serviceassist.FormDomainServiceAssistor;
import com.zyc.form.vo.FormDomainVO;

@Service
public class FormDomainServiceAssistorImpl implements FormDomainServiceAssistor {

	@Autowired
	private FormDomainService domainService;
	
	@Override
	public List<HierarchySelectNode> composeDomains(EmptyNodeType emptyNodeType) {
		List<FormDomainVO> domains = this.domainService.selectAll();
		if(!CollectionUtils.hasElement(domains)) {
			return null;
		}
		
		List<HierarchySelectNode> data = new ArrayList<HierarchySelectNode>();
		for (FormDomainVO domain : domains) {
			data.add(new HierarchySelectNode(domain.getId(), 1, domain.label()));
		}
		
		if(emptyNodeType != null) {
			data.add(0, new HierarchySelectNode(emptyNodeType));
		}
		
		return data;
	}

	@Override
	public List<EntryBean> allDomainEntryBeans(EmptyNodeType emptyNodeType) {
		List<EntryBean> entryBeans = this.domainService.allDomainEntryBeans();
		if(emptyNodeType != null) {
			entryBeans.add(0, new EntryBean(emptyNodeType.getValue(), emptyNodeType.getText()));
		}
		return entryBeans;
	}

}
