package com.zyc.form.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zyc.baselibs.commons.CollectionUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.service.AbstractBaseService;
import com.zyc.baselibs.vo.DeleteMode;
import com.zyc.form.dao.CtrlDimSourceMapper;
import com.zyc.form.dao.FormDomainMapper;
import com.zyc.form.entities.CtrlDimSource;
import com.zyc.form.entities.FormDomain;
import com.zyc.form.service.FormDomainService;
import com.zyc.form.vo.FormDomainVO;

public class FormDomainServiceImpl extends AbstractBaseService implements FormDomainService {

	@Autowired
	private FormDomainMapper formDomainMapper;

	@Autowired
	private CtrlDimSourceMapper ctrlDimSourceMapper;
	
	@Override
	public List<FormDomainVO> selectAll() {
		List<FormDomainVO> result = null;
		
		List<FormDomain> formDomains = this.formDomainMapper.select(new FormDomain().clean());
		if(CollectionUtils.hasElement(formDomains)) {
			result = new ArrayList<FormDomainVO>();
			CtrlDimSource cds = new CtrlDimSource().clean();
			FormDomainVO vo = null;
			for (FormDomain formDomain : formDomains) {
				vo = new FormDomainVO(formDomain);
				
				cds.setFormdomainid(formDomain.getId());
				vo.setCtrlDimSources(this.ctrlDimSourceMapper.select(cds));

				result.add(vo);
			}
		}
		
		return result;
	}

	@Override
	public FormDomainVO selectByFormDomainId(String formdomainid) {
		if(StringUtils.isBlank(formdomainid)) {
			return null;
		}
		
		FormDomain formDomain = this.formDomainMapper.load(formdomainid, FormDomain.class);
		if(formDomain == null) {
			return null;
		}
		
		FormDomainVO vo = new FormDomainVO(formDomain);
		
		CtrlDimSource cds = new CtrlDimSource().clean();
		cds.setFormdomainid(formdomainid);
		
		vo.setCtrlDimSources(this.ctrlDimSourceMapper.select(cds));
		
		return vo;
	}

	@Override
	public FormDomainVO selectByFormDomainCode(String domaincode) {
		if(StringUtils.isBlank(domaincode)) {
			return null;
		}
		
		FormDomain condition = new FormDomain().clean();
		condition.setDomaincode(domaincode);
		
		List<FormDomain> formDomains = this.formDomainMapper.select(condition);
		FormDomain formDomain = CollectionUtils.hasElement(formDomains) ? formDomains.get(0) : null;
		if(formDomain == null) {
			return null;
		}
		
		FormDomainVO vo = new FormDomainVO(formDomain);

		CtrlDimSource cds = new CtrlDimSource().clean();
		cds.setFormdomainid(formDomain.getId());
		
		vo.setCtrlDimSources(this.ctrlDimSourceMapper.select(cds));
		
		return vo;
	}

	@Override
	public FormDomainVO create(FormDomainVO vo) throws Exception {
		
		return null;
	}

	@Override
	public FormDomainVO modify(FormDomainVO vo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(String formdomainid, DeleteMode mode) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteOnLogic(String formdomainid) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteOnPhysical(String formdomainid) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
}
