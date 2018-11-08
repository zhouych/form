package com.zyc.form.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyc.baselibs.asserts.AssertThrowNonRuntime;
import com.zyc.baselibs.commons.CollectionUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.form.dao.FormFieldMapper;
import com.zyc.form.dao.MetaFieldMapper;
import com.zyc.form.data.FormArea;
import com.zyc.form.data.FormType;
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
	private MetaFieldMapper metaFieldMapper;

	@Autowired
	private FormFieldMapper formFieldMapper;

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

}
