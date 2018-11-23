package com.zyc.form.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyc.baselibs.commons.CollectionUtils;
import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.baselibs.service.AbstractBaseService;
import com.zyc.form.dao.MetaFieldMapper;
import com.zyc.form.data.FormType;
import com.zyc.form.entities.MetaField;
import com.zyc.form.service.MetaFieldService;
import com.zyc.form.vo.MetaFieldVO;

@Service
public class MetaFieldServiceImpl extends AbstractBaseService implements MetaFieldService {
	
	@Autowired
	private MetaFieldMapper metaFieldMapper; 

	@Override
	public List<MetaFieldVO> selectMetaFieldByFormtype(FormType formtype) {
		if(formtype == null) {
			return null;
		}
		
		MetaField condition = new MetaField().clean();
		List<MetaField> fields = this.metaFieldMapper.select(condition);
		List<MetaFieldVO> result = null;
		if(CollectionUtils.hasElement(fields)) {
			result = new ArrayList<MetaFieldVO>();
			for (MetaField field : fields) {
				result.add(new MetaFieldVO(field));
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
