package com.zyc.form.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.baselibs.aopv.OverallVerificationRuler;
import com.zyc.baselibs.aopv.ParamVerification;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.ex.BussinessException;
import com.zyc.baselibs.service.AbstractBaseService;
import com.zyc.baselibs.vo.Pagination;
import com.zyc.baselibs.vo.PaginationResult;
import com.zyc.form.dao.FormMapper;
import com.zyc.form.data.FormType;
import com.zyc.form.entities.Form;
import com.zyc.form.service.FormService;
import com.zyc.form.vo.FormDomainVO;
import com.zyc.form.vo.FormVO;

@Service
public class FormServiceImpl extends AbstractBaseService implements FormService {

	public static final List<FormVO> testData = new ArrayList<FormVO>();
	
	@Autowired
	private FormMapper formMapper;
	
	static {
		Form form = null;
		FormVO vo = null;
		for (FormDomainVO domain : FormDomainServiceImpl.testData) {
			for (FormType type : FormType.values()) {
				form = new Form();
				form.init();
				form.setId(UUID.randomUUID().toString());
				form.setFormdomainid(domain.getId());
				form.setFormcode(StringUtils.randomAlphabets(10));
				form.setFormname(form.getFormcode());
				form.setFormtype(type.getValue());
				vo = new FormVO(form);
				testData.add(vo);
			}
		}
	}
	
	@Override
	public List<FormVO> selectAll() {
		if(true) {
			return new ArrayList<FormVO>(testData);
		}
		@SuppressWarnings("unused")
		List<FormVO> result = null;
		return result;
	}

	@Override
	public FormVO selectByFormid(String formid) {
		if(true) {
			for (FormVO vo : testData) {
				if(vo.getId().equals(formid)) {
					return vo;
				}
			}
		}
		
		FormVO vo = null;
		return vo;
	}

	@Override
	public PaginationResult<FormVO> selectByPage(FormVO condition, String searchText, Pagination pagination) {
		PaginationResult<FormVO> result = new PaginationResult<FormVO>();
		
		List<FormVO> rows = new ArrayList<FormVO>();
		rows.addAll(testData);
		result.setRows(rows);
		
		result.setTotal(testData.size());
		
		result.setPagination(pagination);
		
		return result;
	}

	@Override
	public FormVO selectByFormCode(String formdomainid, String formcode) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@ParamVerification(rules = { OverallVerificationRuler.class })
	public FormVO create(FormVO vo) throws Exception {
		Form form = vo.copyEntity();
		//依据id、domain与code进行判重
		if(this.formMapper.load(form.getId(), Form.class) != null || this.selectByFormCode(form.getFormdomainid(), form.getFormcode()) != null) {
			throw new BussinessException("This form domain already exists. (id=" + form.getId() + ", domaincode=" + form.getFormcode() + ")");
		}
		
		form.init();
		int result = this.formMapper.insert(form);
		Form _new =  result > 0 ? this.formMapper.load(form.getId(), Form.class) : null;
		if(_new == null) {
			throw new BussinessException("Created 'FormDomain' failed.");
		}
		
		vo = new FormVO(_new);
		
		return vo;
	}

}
