package com.zyc.form.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.aopv.ParamVerification;
import com.zyc.baselibs.commons.CollectionUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.ex.BussinessException;
import com.zyc.baselibs.ex.IllegalValueException;
import com.zyc.baselibs.service.AbstractBaseService;
import com.zyc.baselibs.vo.DeleteMode;
import com.zyc.form.dao.CtrlDimSourceMapper;
import com.zyc.form.dao.FormDomainMapper;
import com.zyc.form.entities.CtrlDimSource;
import com.zyc.form.entities.FormDomain;
import com.zyc.form.service.FormDomainService;
import com.zyc.form.vo.FormDomainVO;

@Service
public class FormDomainServiceImpl extends AbstractBaseService implements FormDomainService {

	@Autowired
	private FormDomainMapper formDomainMapper;

	@Autowired
	private CtrlDimSourceMapper ctrlDimSourceMapper;
	
	public static final List<FormDomainVO> testData = new ArrayList<FormDomainVO>();
	
	static {
		FormDomainVO vo;
		FormDomain fd;
		CtrlDimSource cds;
		for (int i = 0; i < 10; i++) {
			fd = new FormDomain();
			fd.init();
			fd.setId(UUID.randomUUID().toString());
			fd.setDomaincode(StringUtils.randomAlphabets(10));
			fd.setDomainname(fd.getDomaincode());
			vo = new FormDomainVO(fd);
			
			vo.setCtrlDimSources(new ArrayList<CtrlDimSource>());
			cds = new CtrlDimSource();
			cds.init();
			cds.setId(UUID.randomUUID().toString());
			cds.setFormdomainid(fd.getId());
			cds.setDimid(UUID.randomUUID().toString());
			cds.setDimcode(StringUtils.randomAlphabets(10));
			cds.setDimname(cds.getDimcode());
			cds.setExpression("+@2(" + cds.getDimid() + ");");
			cds.setExpressiontext("包含@成员及后代（" + cds.getDimcode() + "）");
			vo.getCtrlDimSources().add(cds);
			
			testData.add(vo);
		}
	}
	
	@Override
	public List<FormDomainVO> selectAll() {
		if(true) {
			return testData;
		}
		@SuppressWarnings("unused")
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
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@ParamVerification(rules = { FieldRule.class })
	public FormDomainVO create(FormDomainVO vo) throws Exception {
		FormDomain fd = (FormDomain) vo;
		//依据id与code进行判重
		if(this.formDomainMapper.load(fd.getId(), FormDomain.class) != null || this.selectByFormDomainCode(fd.getDomaincode()) != null) {
			throw new BussinessException("This form domain already exists. (id=" + fd.getId() + ", domaincode=" + fd.getDomaincode() + ")");
		}
		
		fd.init();
		int result = this.formDomainMapper.insert(fd);
		FormDomain newfd =  result > 0 ? this.formDomainMapper.load(fd.getId(), FormDomain.class) : null;
		if(newfd == null) {
			throw new BussinessException("Created 'FormDomain' failed.");
		}
		
		FormDomainVO newvo = new FormDomainVO(newfd);
		newvo.setCtrlDimSources(this.create(vo.getCtrlDimSources(), newfd.getId()));
		return newvo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<CtrlDimSource> create(List<CtrlDimSource> cdss, String formdomainid) throws Exception {
		List<CtrlDimSource> result = null;
		if(CollectionUtils.hasElement(cdss)) {
			result = new ArrayList<CtrlDimSource>();
			CtrlDimSource newcds = null;
			for (CtrlDimSource cds : cdss) {
				newcds = this.create(cds, formdomainid);
				if(newcds == null) {
					throw new BussinessException("Created 'CtrlDimSource' failed.");
				}
				result.add(newcds);
			}
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@ParamVerification(rules = { FieldRule.class })
	public CtrlDimSource create(CtrlDimSource cds, String formdomainid) throws Exception {
		//formdomainid为表单域主键ID，该ID为当前待添加数据的外键（表单域主键ID）的正确值
		if(StringUtils.isBlank(formdomainid) || !formdomainid.equals(cds.getFormdomainid())) {
			throw new IllegalValueException(String.format("The data 'formdomainid' does not match. (formdomainid: %s!=%s)", cds.getFormdomainid(), formdomainid));
		}
		
		//依据id与formdomainid进行判重
		if(this.ctrlDimSourceMapper.load(cds.getId(), CtrlDimSource.class) != null) {
			throw new BussinessException("This dimension source already exists. (id=" + cds.getId() + ", formdomainid=" + cds.getFormdomainid() + ")");
		}
		
		cds.init();
		int result = this.ctrlDimSourceMapper.insert(cds);
		return  result > 0 ? this.ctrlDimSourceMapper.load(cds.getId(), CtrlDimSource.class) : null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@ParamVerification(rules = { FieldRule.class })
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
