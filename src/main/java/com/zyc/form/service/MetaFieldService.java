package com.zyc.form.service;

import java.util.List;

import com.zyc.baselibs.service.EntityDeleteService;
import com.zyc.baselibs.service.EnumService;
import com.zyc.form.data.FormType;
import com.zyc.form.vo.MetaFieldVO;

public interface MetaFieldService extends EntityDeleteService, EnumService {

	List<MetaFieldVO> selectMetaFieldByFormtype(FormType formtype);

}
