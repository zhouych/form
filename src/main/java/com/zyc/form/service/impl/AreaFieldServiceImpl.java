package com.zyc.form.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.service.AbstractBaseService;
import com.zyc.baselibs.vo.EntryBean;
import com.zyc.form.data.FormArea;
import com.zyc.form.data.FormType;
import com.zyc.form.entities.FormField;
import com.zyc.form.entities.MetaField;
import com.zyc.form.service.AreaFieldService;
import com.zyc.form.vo.FormFieldVO;
import com.zyc.form.vo.FormVO;
import com.zyc.form.vo.MetaFieldVO;

@Service
public class AreaFieldServiceImpl extends AbstractBaseService implements AreaFieldService {
	
	static final boolean testFlag = true;
	
	static final FormArea[] test_Areas = new FormArea[] { FormArea.MAIN, FormArea.DETAIL, FormArea.REVERSAL, FormArea.PAYMENT, FormArea.REPAYMENT, FormArea.BACKUP01 };
	
	static final List<MetaFieldVO> test_MetaField = new ArrayList<MetaFieldVO>();
	
	static final List<FormFieldVO> test_FormField = new ArrayList<FormFieldVO>();
	
	static {
		MetaField mf;
		MetaFieldVO mfvo;
		FormField ff;
		FormFieldVO ffvo;
		for (FormVO formvo : FormServiceImpl.testData) {
			for (int i = 0; i < 30; i++) {
				mf = new MetaField();
				mf.init();
				mf.setId(UUID.randomUUID().toString());
				mf.setFormarea(test_Areas[i % 6].getValue());
				mf.setFieldvalue(StringUtils.randomAlphabets(5));
				mf.setFieldname(mf.getFieldvalue());
				mfvo = new MetaFieldVO(mf);
				test_MetaField.add(mfvo);
				
				ff = new FormField();
				ff.init();
				ff.setId(UUID.randomUUID().toString());
				ff.setMetafieldid(mf.getId());
				ff.setFieldvalue(mf.getFieldvalue());
				ff.setFieldname(mf.getFieldname());
				ff.setFormid(formvo.getId());
				ff.setFormarea(mf.getFormarea());
				ffvo = new FormFieldVO(ff);
				test_FormField.add(ffvo);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<EntryBean> selectAreaByFormtype(FormType formtype) {
		return FormArea.toList(formtype);
	}

	@Override
	public List<FormFieldVO> selectFormFieldByFormid(String formid) {
		if(testFlag) {
			List<FormFieldVO> list = new ArrayList<FormFieldVO>();
			for (FormFieldVO ffvo : test_FormField) {
				if(ffvo.getFormid().equals(formid)) {
					list.add(ffvo);
				}
			}
			return list;
		}
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MetaFieldVO> selectMetaFieldByFormtype(FormType formtype) {
		if(testFlag) {
			List<MetaFieldVO> list = new ArrayList<MetaFieldVO>();
			for (MetaFieldVO mfvo : test_MetaField) {
				if(mfvo.getFormarea().equals(formtype.getValue())) {
					list.add(mfvo);
				}
			}
			return list;
		}
		
		// TODO Auto-generated method stub
		return null;
	}

}
