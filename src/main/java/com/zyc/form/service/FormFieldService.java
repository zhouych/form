package com.zyc.form.service;

import java.util.List;

import com.zyc.baselibs.service.EntityDeleteService;
import com.zyc.baselibs.vo.Pagination;
import com.zyc.baselibs.vo.PaginationResult;
import com.zyc.form.entities.FormField;
import com.zyc.form.vo.FormFieldVO;

public interface FormFieldService extends EntityDeleteService {

	List<FormFieldVO> selectFormFieldByFormid(String formid);

	PaginationResult<FormFieldVO> selectByPage(FormFieldVO condition, String keyword, Pagination pagination);

	/**
	 * 刷新表单字段：将指定表单的字段进行一次刷新，确保所有字段信息的时效性。
	 * @param formid 表单id
	 * @return
	 */
	boolean refresh(String formid) throws Exception;

	FormFieldVO selectByFormfieldid(String formfieldid);
	
	FormFieldVO selectByFieldvalue(String formid, String formarea, String fieldvalue);
	
	FormField loadFormField(String formid, String formarea, String fieldvalue);

	FormFieldVO create(FormFieldVO vo) throws Exception;

	FormFieldVO modify(FormFieldVO vo) throws Exception;

}
