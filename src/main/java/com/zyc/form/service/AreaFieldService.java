package com.zyc.form.service;

import java.util.List;

import com.zyc.baselibs.service.EntityDeleteService;
import com.zyc.baselibs.vo.EntryBean;
import com.zyc.form.data.FormType;
import com.zyc.form.vo.FormFieldVO;
import com.zyc.form.vo.MetaFieldVO;

public interface AreaFieldService extends EntityDeleteService {

	/**
	 * 查询指定表单类型下的所有表单区域
	 * @param formtype 表单类型。
	 * @return 
	 * 表单区域集合。</br>
	 * formtype=null时，返回所有的表单区域。</br>
	 * 如果该方法内部没有抛出异常，将必定返回一个非空的集合。</br>
	 */
	List<EntryBean> selectAreaByFormtype(FormType formtype);

	List<FormFieldVO> selectFormFieldByFormid(String formid);

	List<MetaFieldVO> selectMetaFieldByFormtype(FormType formtype);

}
