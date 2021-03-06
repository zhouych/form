package com.zyc.form.service;

import java.util.List;

import com.zyc.baselibs.service.EntityDeleteService;
import com.zyc.baselibs.service.EntityLoadService;
import com.zyc.baselibs.service.EnumService;
import com.zyc.baselibs.vo.Pagination;
import com.zyc.baselibs.vo.PaginationResult;
import com.zyc.form.entities.Form;
import com.zyc.form.vo.FormVO;

public interface FormService extends EntityDeleteService, EnumService, EntityLoadService<Form> {

	List<FormVO> selectAll();
	
	FormVO selectByFormid(String formid);

	PaginationResult<FormVO> selectByPage(FormVO condition, String keyword, Pagination pagination);
	
	FormVO selectByFormCode(String formdomainid, String formcode); 
	
	FormVO create(FormVO form) throws Exception;

	FormVO modify(FormVO form) throws Exception;
}
