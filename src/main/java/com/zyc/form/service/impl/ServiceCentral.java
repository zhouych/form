package com.zyc.form.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.zyc.baselibs.asserts.AssertThrowNonRuntime;
import com.zyc.baselibs.commons.CollectionUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.ex.BussinessException;
import com.zyc.baselibs.vo.Pagination;
import com.zyc.baselibs.vo.SortField;
import com.zyc.form.dao.FormFieldMapper;
import com.zyc.form.dao.FormMapper;
import com.zyc.form.dao.MetaFieldMapper;
import com.zyc.form.data.FormArea;
import com.zyc.form.data.FormType;
import com.zyc.form.entities.Form;
import com.zyc.form.entities.FormField;
import com.zyc.form.entities.MetaField;

/**
 * 抽取服务层的通用业务处理。
 * @author zhouyancheng
 *
 */
@Service
class ServiceCentral {
	
	private static final Logger logger = Logger.getLogger(ServiceCentral.class);
	
	@Autowired
	private FormMapper formMapper;
	
	@Autowired
	private MetaFieldMapper metaFieldMapper;

	@Autowired
	private FormFieldMapper formFieldMapper;
	
	/**
	 * 加载元字段
	 * @param formarea 必选参数，元字段所在的表单区域。
	 * @param fieldvalue 必选参数，元字段的值。
	 * @return
	 */
	public MetaField loadFormMetaField(String formarea, String fieldvalue) {
		Assert.hasText(formarea, "The parameter 'formid' is null or empty.");
		Assert.hasText(fieldvalue, "The parameter 'formid' is null or empty.");
		MetaField mf = new MetaField().clean();
		mf.setFormarea(formarea);
		mf.setFieldvalue(fieldvalue);
		List<MetaField> mfs = this.metaFieldMapper.select(mf);
		if(mfs != null && mfs.size() > 1) {
			throw new RuntimeException("Data error: The meta field is not unique. (formarea=" + formarea + "; fieldvalue=" + fieldvalue + ")");
		}
		return CollectionUtils.hasElement(mfs) ? mfs.get(0) : null;
	}
	
	/**
	 * 申请一个自定义字段
	 * @param formid 必选参数，代申请自定义字段的表单ID。
	 * @param formarea 必选参数，代申请自定义字段的表单区域。
	 * @return {@link MetaField} 的实例对象
	 * @throws Exception
	 */
	public MetaField applyItemField(String formid, String formarea) throws Exception {
		Assert.hasText(formid, "The parameter 'formid' is null or empty.");
		Form form = this.formMapper.load(formid, Form.class);
		Assert.notNull(form, "The form does not exist. (formid=" + formid + ")");
		
		FormField ffc = new FormField().clean();
		ffc.setFormid(formid);
		ffc.setFormarea(formarea);
		ffc.setSysfield(false);
		List<FormField> ffs = this.formFieldMapper.select(ffc);
		int usedMax = 0;
		if(CollectionUtils.hasElement(ffs)) {
			int max;
			for (FormField ff : ffs) {
				max = FormField.getItemIndex(ff.getFieldvalue());
				if(max > 0 && max > usedMax) {
					usedMax = max;
				}
			}
		}
		
		String item = "item" + (usedMax >= 9 ? "" : "0") + (usedMax + 1);
		MetaField mf = this.loadFormMetaField(formarea, item);
		if(mf == null || true == mf.getSysfield()) {
			throw new BussinessException("This custom field is not supported by the system. (fieldvalue=" + item + ")");
		}
		return mf;
	}

	public List<MetaField> selectFormMetaFields(String formtype) {
		return this.selectFormMetaFields(StringUtils.toEnumIgnoreCase(FormType.class, formtype));
	}
	
	public List<MetaField> selectFormMetaFields(FormType formtype) {
		Map<String, Object> field2values = new HashMap<String, Object>();
		field2values.put("formarea", FormArea.values(formtype));
		return this.metaFieldMapper.whereIn(field2values, MetaField.class);
	}
	
	public List<MetaField> selectFormMetaFields(String formtype, Boolean sysfield) {
		return this.selectFormMetaFields(StringUtils.toEnumIgnoreCase(FormType.class, formtype), sysfield);
	}
	
	public List<MetaField> selectFormMetaFields(FormType formtype, Boolean sysfield) {
		Map<String, Object> field2values = new HashMap<String, Object>();
		field2values.put("formarea", FormArea.values(formtype));
		if(sysfield != null) {
			field2values.put("sysfield", sysfield ? "1" : "0");
		}
		return this.metaFieldMapper.whereIn(field2values, MetaField.class);
	}
	
	public FormField insertFormField(MetaField mf, String formid) throws Exception {
		FormField field = new FormField(mf);
		field.init();
		field.createIdWhenNot();
		field.setMetafieldid(mf.getId());
		field.setFormid(formid);
		boolean success = this.formFieldMapper.insert(field) > 0;
		if(!success) {
			logger.warn("The current form field failed to refresh. (field = " + StringUtils.instanceDetail(field) + ")");
		}
		return success ? this.formFieldMapper.load(field.getId(), FormField.class) : null;
	}
	
	/**
	 * 刷新指定表单的所有字段
	 * @param formid 必选参数，指定的表单。
	 * @param formtype 必选参数，表单类型（类型不同，意味着表单区域的不同，字段也就有差异）。
	 * @param visitor 可选参数，每刷新一个字段，都可通过该参数对该刷新的字段进行操作。
	 * @throws Exception
	 */
	public void refreshFormFields(String formid, FormType formtype, Visitor<FormField, Boolean> visitor) throws Exception {
		AssertThrowNonRuntime.hasText(formid, "This parameter 'formid' is null or empty.");
		AssertThrowNonRuntime.notNull(formtype, "This parameter 'formtype' is null or empty.");
		
		List<MetaField> mfs = this.selectFormMetaFields(formtype, true);
		if(CollectionUtils.hasElement(mfs)) {
			FormField ffc = new FormField().clean();
			ffc.setFormid(formid);
			List<FormField> ffs = this.formFieldMapper.select(ffc);

			FormField newest = null;
			for (MetaField mf : mfs) {
				boolean exists = false;
				if(CollectionUtils.hasElement(ffs)) {
					for (FormField ff : ffs) {
						if(mf.getId().equals(ff.getMetafieldid())) {
							exists = true;
							break;
						}
					}
				}
				
				if(!exists) {
					newest = this.insertFormField(mf, formid);
					if(visitor != null && visitor.visit(newest)) {
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 调整字段的排序策略
	 * @param pagination 必选参数。分页参数对象实例。
	 * @param strategies 必选参数。排序策略，固定的排序优先级，据此对<code>pagination</code>中的排序策略进行调整。
	 * <p>该<code>strategies</code>的元素取实体的字段名，例如：<code>new String[] { "field1", "field2" }</code>。</p>
	 */
	public void adjustSortStrategy(Pagination pagination, String[] strategies) {
		String name; 
		boolean stopPrev = false;
		List<SortField> sorts;
		for (int i = 0; i < strategies.length; i++) {
			name = strategies[i];
			
			if(name.equals(pagination.getOrder())) {
				stopPrev = true;
				continue;
			}
			
			if(stopPrev) {
				if(pagination.getNextSorts() == null) {
					pagination.setNextSorts(new ArrayList<SortField>());
				}
				sorts = pagination.getNextSorts();
			} else {
				if(pagination.getPrevSorts() == null) {
					pagination.setPrevSorts(new ArrayList<SortField>());
				}
				sorts = pagination.getPrevSorts();
			}
			
			if((pagination.getPrevSorts() == null || !pagination.getPrevSorts().contains(name)) 
					&& (pagination.getNextSorts() == null || !pagination.getNextSorts().contains(name))) {
				sorts.add(new SortField(name, true));
			}
		}
	}

}
