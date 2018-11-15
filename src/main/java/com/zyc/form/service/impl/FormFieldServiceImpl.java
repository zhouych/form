package com.zyc.form.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.annotation.FieldRuleUtils;
import com.zyc.baselibs.aopv.OverallVerificationRuler;
import com.zyc.baselibs.aopv.ParamVerification;
import com.zyc.baselibs.asserts.AssertThrowNonRuntime;
import com.zyc.baselibs.commons.CollectionUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.data.DataStatus;
import com.zyc.baselibs.ex.BussinessException;
import com.zyc.baselibs.service.AbstractSelectByPageService;
import com.zyc.baselibs.service.ValueObjectableUtils;
import com.zyc.baselibs.vo.DeleteMode;
import com.zyc.baselibs.vo.Pagination;
import com.zyc.baselibs.vo.PaginationResult;
import com.zyc.form.dao.FormFieldMapper;
import com.zyc.form.dao.FormMapper;
import com.zyc.form.data.FormType;
import com.zyc.form.entities.Form;
import com.zyc.form.entities.FormField;
import com.zyc.form.entities.MetaField;
import com.zyc.form.service.FormFieldService;
import com.zyc.form.vo.FormFieldVO;

@Service
public class FormFieldServiceImpl extends AbstractSelectByPageService implements FormFieldService {
	
	//private static final Logger logger = Logger.getLogger(FormFieldServiceImpl.class);
	
	@Autowired
	private FormFieldMapper formFieldMapper;
	
	@Autowired
	private FormMapper formMapper;

	@Autowired
	private ServiceCentral central;
	
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
		
		//固定按formid与formarea进行最优先排序，来自其他指定排序规则均在此基础下再进行排序。
		this.central.adjustSortStrategy(pagination, new String[] { FormField.FIELD_FORMID, FormField.FIELD_FORMAREA });
		
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

	@Override
	public FormFieldVO selectByFormfieldid(String formfieldid) {
		if(StringUtils.isBlank(formfieldid)) {
			return null;
		}
		
		FormField form = this.formFieldMapper.load(formfieldid, FormField.class);
		if(form == null) {
			return null;
		}
		
		FormFieldVO vo = new FormFieldVO(form);
		return vo;
	}

	public FormFieldVO selectByFieldvalue(String formid, String formarea, String fieldvalue) {
		if(StringUtils.isBlank(formid) || StringUtils.isBlank(formid) || StringUtils.isBlank(fieldvalue)) {
			return null;
		}
		
		Form form = this.formMapper.load(formid, Form.class);
		if(form == null) {
			return null;
		}
		
		FormField field = this.loadFormField(formid, formarea, fieldvalue);
		FormFieldVO vo = new FormFieldVO(field);
		vo.setFormname(form.label());		
		return vo;
	}
	
	public FormField loadFormField(String formid, String formarea, String fieldvalue) {
		Assert.hasText(formid, "The parameter 'formid' is a null or empty.");
		Assert.hasText(formarea, "The parameter 'formarea' is a null or empty.");
		Assert.hasText(fieldvalue, "The parameter 'fieldvalue' is a null or empty.");
		
		FormField condition = new FormField().clean();
		condition.setFormid(formid);
		condition.setFormarea(formarea);
		condition.setFieldvalue(fieldvalue);
		
		List<FormField> fields = this.formFieldMapper.select(condition);
		if(!CollectionUtils.hasElement(fields)) {
			return null;
		}

		if(fields.size() > 1) {
			throw new RuntimeException("Data error: The field is not unique. (formid=" + formid + "; formarea=" + formarea + "; fieldvalue=" + fieldvalue + ")");
		}
		
		return fields.get(0);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@ParamVerification(rules = { OverallVerificationRuler.class })
	public FormFieldVO create(FormFieldVO vo) throws Exception {
		FormField form = vo.copyEntity();
		//依据id、domain与code进行判重
		if(this.formFieldMapper.load(form.getId(), FormField.class) != null || this.loadFormField(form.getFormid(), form.getFormarea(), form.getFieldvalue()) != null) {
			throw new BussinessException("This form already exists. (formfieldid=" + form.getId() + "; formid=" + form.getFormid() + "; fieldvalue=" + form.getFieldvalue() + ")");
		}
		
		form.init();
		int result = this.formFieldMapper.insert(form);
		
		FormField newest =  result > 0 ? this.formFieldMapper.load(form.getId(), FormField.class) : null;
		if(newest == null) {
			throw new BussinessException("Form field creation failed.");
		}
		
		vo = new FormFieldVO(newest);
		
		return vo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@ParamVerification(rules = { FieldRule.class })
	public FormFieldVO modify(FormFieldVO vo) throws Exception {
		FormField field = vo.copyEntity();
		
		FormField old = this.formFieldMapper.load(field.getId(), FormField.class);
		if(old == null || !field.businessEquals(old)) {
			throw new BussinessException("This form field does not exist or data does not matchs. (formid=" + field.getFormid() + "; formarea=" + field.getFormarea() + "; fieldvalue=" + field.getFieldvalue() + ")");
		}

		BeanUtils.copyProperties(field, old, FieldRuleUtils.externalUneditableFields(old));
		this.update(this.formFieldMapper, old, ACTION_UPDATE);

		Form form = this.formMapper.load(vo.getFormid(), Form.class);
		
		FormFieldVO newest = new FormFieldVO(old);
		newest.setFormname(form.label());
		return newest;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FormFieldVO applyItemField(String formid, String formarea) throws Exception {
		MetaField mf = this.central.applyItemField(formid, formarea);
		FormFieldVO vo = FormFieldVO.newInstance();
		vo.setMetafieldid(mf.getId());
		vo.setFormid(formid);
		vo.setFieldvalue(mf.getFieldvalue());
		vo.setSysfield(false); //自定义字段与系统字段是对立的
		vo.setEditable(true); //自定义字段默认可编辑
		return vo;
	}

	@Override
	public String applyItemFieldValue(String formid, String formarea) throws Exception {
		return this.central.applyItemField(formid, formarea).getFieldvalue();
	}
	
	/**
	 * {@inheritDoc}
	 * @param formid 表单id，如果传入空值，将抛出异常
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean refresh(String formid) throws Exception {
		AssertThrowNonRuntime.hasText(formid, "This parameter 'formid' is null or empty.");
		Form form = this.formMapper.load(formid, Form.class);
		AssertThrowNonRuntime.notNull(form, "The form does not exist. (formid=" + formid + ")");
		FormType formtype = StringUtils.toEnumIgnoreCase(FormType.class, form.getFormtype());
		this.central.refreshFormFields(formid, formtype, null);
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
