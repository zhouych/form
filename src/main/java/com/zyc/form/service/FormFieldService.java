package com.zyc.form.service;

import java.util.List;

import com.zyc.baselibs.service.EntityDeleteService;
import com.zyc.form.vo.FormFieldVO;

public interface FormFieldService extends EntityDeleteService {

	List<FormFieldVO> selectFormFieldByFormid(String formid);

}
