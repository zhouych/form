package com.zyc.form.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.baselibs.asserts.AssertThrowNonRuntime;
import com.zyc.baselibs.commons.CollectionUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.entities.DataStatus;
import com.zyc.baselibs.service.AbstractSelectByPageService;
import com.zyc.baselibs.service.ValueObjectableUtils;
import com.zyc.baselibs.vo.DeleteMode;
import com.zyc.baselibs.vo.Pagination;
import com.zyc.baselibs.vo.PaginationResult;
import com.zyc.form.dao.FormFieldMapper;
import com.zyc.form.dao.FormMapper;
import com.zyc.form.dao.MetaFieldMapper;
import com.zyc.form.entities.Form;
import com.zyc.form.entities.FormField;
import com.zyc.form.entities.MetaField;
import com.zyc.form.service.FormFieldService;
import com.zyc.form.vo.FormFieldVO;

@Service
public class FormFieldServiceImpl extends AbstractSelectByPageService implements FormFieldService {
	
	private static final Logger logger = Logger.getLogger(FormFieldServiceImpl.class);
	
	@Autowired
	private FormFieldMapper formFieldMapper;

	@Autowired
	private MetaFieldMapper metaFieldMapper;
	
	@Autowired
	private FormMapper formMapper;
	
	@Override
	public List<FormFieldVO> selectFormFieldByFormid(String formid) {
		if(StringUtils.isBlank(formid)) {
			return null;
		}
		
		FormField condition = new FormField().clean();
		condition.setFormid(formid);
		List<FormField> fields = this.formFieldMapper.select(condition);
		List<FormFieldVO> result = null;
		if(CollectionUtils.hasElement(fields)) {
			result = new ArrayList<FormFieldVO>();
			for (FormField field : fields) {
				result.add(new FormFieldVO(field));
			}
		}
		
		return result;
	}

	@Override
	public PaginationResult<FormFieldVO> selectByPage(FormFieldVO condition, String keyword, Pagination pagination) {
		FormField field = condition.copyEntity();

		Map<String, Form> formCache = new HashMap<String, Form>();
		
		List<FormField> fields = this.selectByPage(this.formFieldMapper, field, keyword, pagination);
		List<FormFieldVO> rows = ValueObjectableUtils.fromEntities(fields, FormFieldVO.class, FormField.class, new Visitor<Map<String, FormField>, FormFieldVO>() {
			@Override
			public FormFieldVO visit(Map<String, FormField> map) {
				FormFieldVO vo = (FormFieldVO) map.get("vo");
				if(!formCache.containsKey(vo.getFormid())) {
					formCache.put(vo.getFormid(), formMapper.load(vo.getFormid(), Form.class));
				}
				vo.setFormname(formCache.get(vo.getFormid()).label());
				return vo;
			}
		});
		
		formCache.clear();
		
		PaginationResult<FormFieldVO> result = new PaginationResult<FormFieldVO>();
		result.setRows(rows);
		result.setTotal(this.selectTotalCount(this.formFieldMapper, field, keyword, pagination));
		result.setPagination(pagination);
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @param formid 表单id，如果传入空值，将抛出异常
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean refresh(String formid) throws Exception {
		AssertThrowNonRuntime.hasText(formid, "This parameter 'formid' is null or empty.");
		
		MetaField mfc = new MetaField().clean();
		List<MetaField> mfs = this.metaFieldMapper.select(mfc);
		if(CollectionUtils.hasElement(mfs)) {
			FormField ffc = new FormField().clean();
			ffc.setFormid(formid);
			List<FormField> ffs = this.formFieldMapper.select(ffc);
			
			FormField ins;
			for (MetaField mf : mfs) {
				boolean exists = false;
				for (FormField ff : ffs) {
					if(mf.getId().equals(ff.getMetafieldid())) {
						exists = true;
						break;
					}
				}
				
				if(!exists) {
					ins = new FormField(mf);
					ins.init();
					ins.createIdWhenNot();
					ins.setMetafieldid(mf.getId());
					ins.setFormid(formid);
					if(this.formFieldMapper.insert(ins) > 0) {
						logger.warn("The current form field failed to refresh. (field = " + StringUtils.instanceDetail(ins) + ")");
					}
				}
			}
		}
		
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean deleteOnLogic(String formfieldid) throws Exception {
		FormField field = this.loadDeletableEntity(formfieldid, FormField.class, this.formFieldMapper, DeleteMode.LOGIC);
		field.setDatastatus(DataStatus.DELETED.getValue());
		int result = this.update(this.formFieldMapper, field, ACTION_DELETE);
		return result > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean deleteOnPhysical(String formfieldid) throws Exception {
		FormField field = this.loadDeletableEntity(formfieldid, FormField.class, this.formFieldMapper, DeleteMode.PHYSICAL);
		int result = this.formMapper.deleteById(field.getId(), field.getVersion(), Form.class);
		return result > 0;
	}

}
