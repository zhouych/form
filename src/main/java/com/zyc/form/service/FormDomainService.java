
package com.zyc.form.service;

import java.util.List;

import com.zyc.baselibs.service.EntityDeleteService;
import com.zyc.baselibs.service.EnumService;
import com.zyc.baselibs.vo.EntryBean;
import com.zyc.baselibs.vo.Pagination;
import com.zyc.form.entities.CtrlDimSource;
import com.zyc.form.vo.CtrlDimSourceOptionVO;
import com.zyc.form.vo.FormDomainQueryVO;
import com.zyc.form.vo.FormDomainVO;

public interface FormDomainService extends EntityDeleteService, EnumService {

	List<EntryBean> allDomainEntryBeans();
	
	List<FormDomainVO> selectAll();
	
	List<FormDomainVO> selectByPage(FormDomainQueryVO condition, String keyword, Pagination pagination);
	
	FormDomainVO selectByFormDomainId(String formdomainid);
	
	FormDomainVO selectByFormDomainCode(String domaincode);

	FormDomainVO create(FormDomainVO vo) throws Exception;

	List<CtrlDimSource> createCtrlDimSource(List<CtrlDimSourceOptionVO> vos, String formdomainid) throws Exception;
	
	CtrlDimSource createCtrlDimSource(CtrlDimSourceOptionVO vo, String formdomainid) throws Exception;
	
	FormDomainVO modify(FormDomainVO vo) throws Exception;
}
