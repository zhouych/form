package com.zyc.form.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.service.AbstractBaseService;
import com.zyc.baselibs.vo.Pagination;
import com.zyc.baselibs.vo.PaginationResult;
import com.zyc.form.data.FormType;
import com.zyc.form.entities.Form;
import com.zyc.form.service.FormService;
import com.zyc.form.vo.FormDomainVO;
import com.zyc.form.vo.FormVO;

@Service
public class FormServiceImpl extends AbstractBaseService implements FormService {

	public static final List<FormVO> testData = new ArrayList<FormVO>();
	
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
	public FormVO create(FormVO form) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
