package com.zyc.form.service;

import java.util.List;

import com.zyc.baselibs.service.EntityDeleteService;
import com.zyc.form.data.FormType;
import com.zyc.form.vo.MetaFieldVO;

public interface MetaFieldService extends EntityDeleteService {

	List<MetaFieldVO> selectMetaFieldByFormtype(FormType formtype);

}
