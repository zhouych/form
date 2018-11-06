package com.zyc.form.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyc.baselibs.commons.CollectionUtils;
import com.zyc.baselibs.service.AbstractBaseService;
import com.zyc.form.dao.FormFieldMapper;
import com.zyc.form.entities.FormField;
import com.zyc.form.service.FormFieldService;
import com.zyc.form.vo.FormFieldVO;

@Service
public class FormFieldServiceImpl extends AbstractBaseService implements FormFieldService {
	
	@Autowired
	private FormFieldMapper formFieldMapper; 
	
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
	public boolean deleteOnLogic(String entityId) throws Exception {
		
		return false;
	}

	@Override
	public boolean deleteOnPhysical(String entityId) throws Exception {
		
		return false;
	}

}
