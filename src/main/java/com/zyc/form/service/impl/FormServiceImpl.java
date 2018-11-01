package com.zyc.form.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.baselibs.aopv.OverallVerificationRuler;
import com.zyc.baselibs.aopv.ParamVerification;
import com.zyc.baselibs.commons.CollectionUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.ex.BussinessException;
import com.zyc.baselibs.service.AbstractSelectByPageService;
import com.zyc.baselibs.vo.Pagination;
import com.zyc.baselibs.vo.PaginationResult;
import com.zyc.form.dao.FormDomainMapper;
import com.zyc.form.dao.FormMapper;
import com.zyc.form.data.FormType;
import com.zyc.form.entities.Form;
import com.zyc.form.entities.FormDomain;
import com.zyc.form.service.FormService;
import com.zyc.form.vo.FormDomainVO;
import com.zyc.form.vo.FormVO;

@Service
public class FormServiceImpl extends AbstractSelectByPageService implements FormService {

	public static final List<FormVO> testData = new ArrayList<FormVO>();
	
	@Autowired
	private FormMapper formMapper;
	
	@Autowired
	private FormDomainMapper domainMapper;
	
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
		List<Form> forms = this.formMapper.select(new Form().clean());
		return this.fromEntities(forms);
	}

	@Override
	public FormVO selectByFormid(String formid) {
		if(StringUtils.isBlank(formid)) {
			return null;
		}
		
		Form form = this.formMapper.load(formid, Form.class);
		if(form == null) {
			return null;
		}
		
		FormVO vo = new FormVO(form);
		return vo;
	}

	@Override
	public PaginationResult<FormVO> selectByPage(FormVO condition, String keyword, Pagination pagination) {
		Form form = condition.copyEntity();
		
		List<String> ids = new ArrayList<String>();
		List<Form> forms = this.selectByPage(this.formMapper, form, keyword, pagination);
		List<FormVO> rows = this.fromEntities(forms, new Visitor<FormVO, FormVO>() {
			@Override
			public FormVO visit(FormVO vo) {
				if(StringUtils.isNotBlank(vo.getFormdomainid()) && !ids.contains(vo.getFormdomainid())) {
					ids.add(vo.getFormdomainid());
				}
				return vo;
			}
		});
		
		if(CollectionUtils.hasElement(rows) && !ids.isEmpty()) {
			Map<String, Object> field2values = new HashMap<String, Object>();
			field2values.put("id", ids);
			List<FormDomain> domains = this.domainMapper.whereIn(field2values, FormDomain.class);
			if(CollectionUtils.hasElement(domains)) {
				FormDomain domain;
				for (int i = domains.size() - 1; i >= 0 ; i--) {
					domain = domains.get(i);
					boolean matched = false;
					for (FormVO row : rows) {
						if(domain.getId().equals(row.getFormdomainid())) {
							row.setFormdomainname(domain.label());
							matched = true;
						}
					}
					if(matched) {
						domains.remove(i);
					}
				}
			}
		}
		

		PaginationResult<FormVO> result = new PaginationResult<FormVO>();
		result.setRows(rows);
		result.setTotal(this.selectTotalCount(this.formMapper, form, keyword, pagination));
		result.setPagination(pagination);
		return result;
	}
	
	private List<FormVO> fromEntities(List<Form> forms, Visitor<FormVO, FormVO> visitor) {
		List<FormVO> result = null;
		if(CollectionUtils.hasElement(forms)) {
			result = new ArrayList<FormVO>();
			FormVO vo;
			for (Form form : forms) {
				vo = new FormVO(form);
				if(visitor != null) {
					visitor.visit(vo);
				}
				result.add(vo);
			}
		}
		return result;
	}

	private List<FormVO> fromEntities(List<Form> forms) {
		return this.fromEntities(forms, null);
	}

	@Override
	public FormVO selectByFormCode(String formdomainid, String formcode) {
		if(StringUtils.isBlank(formdomainid) || StringUtils.isBlank(formcode)) {
			return null;
		}
		
		Form condition = new Form().clean();
		condition.setFormdomainid(formdomainid);
		condition.setFormcode(formcode);
		
		List<Form> forms = this.formMapper.select(condition);
		
		if(!CollectionUtils.hasElement(forms)) {
			return null;
		}

		if(forms.size() > 1) {
			//同一表单域下，编码应该唯一。如果出现查询结果不唯一，则表明数据表中的数据已经存在问题了，应该终止业务，防止错误蔓延。
			throw new RuntimeException("The form domain has forms with the same encoding. (domain=" + formdomainid + "; formcode=" + formcode + ")");
		}
		
		FormVO vo = new FormVO(forms.get(0));
		return vo;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@ParamVerification(rules = { OverallVerificationRuler.class })
	public FormVO create(FormVO vo) throws Exception {
		Form form = vo.copyEntity();
		//依据id、domain与code进行判重
		if(this.formMapper.load(form.getId(), Form.class) != null || this.selectByFormCode(form.getFormdomainid(), form.getFormcode()) != null) {
			throw new BussinessException("This form already exists. (formid=" + form.getId() + "; formdomainid=" + form.getFormdomainid() + "; formcode=" + form.getFormcode() + ")");
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

	@Override
	public FormVO modify(FormVO form) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteOnLogic(String formid) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteOnPhysical(String formid) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
}
