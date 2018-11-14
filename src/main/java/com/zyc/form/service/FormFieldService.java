package com.zyc.form.service;

import java.util.List;

import com.zyc.baselibs.service.EntityDeleteService;
import com.zyc.baselibs.service.EnumService;
import com.zyc.baselibs.vo.Pagination;
import com.zyc.baselibs.vo.PaginationResult;
import com.zyc.form.entities.FormField;
import com.zyc.form.vo.FormFieldVO;

public interface FormFieldService extends EntityDeleteService, EnumService {

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

	/**
	 * 为表单申请一个自定义字段
	 * @param formid 必选参数，代申请自定义字段的表单ID。
	 * @param formarea 必选参数，代申请自定义字段的表单区域。
	 * @return
	 */
	FormFieldVO applyItemField(String formid, String formarea) throws Exception;

}
