
package com.zyc.form.service;

import java.util.List;

import com.zyc.baselibs.vo.DeleteMode;
import com.zyc.baselibs.vo.EntryBean;
import com.zyc.baselibs.vo.Pagination;
import com.zyc.form.entities.CtrlDimSource;
import com.zyc.form.vo.CtrlDimSourceOptionVO;
import com.zyc.form.vo.FormDomainQueryVO;
import com.zyc.form.vo.FormDomainVO;

public interface FormDomainService {

	List<EntryBean> allDomainEntryBeans();
	
	List<FormDomainVO> selectAll();
	
	List<FormDomainVO> selectByPage(FormDomainQueryVO condition, String keyword, Pagination pagination);
	
	FormDomainVO selectByFormDomainId(String formdomainid);
	
	FormDomainVO selectByFormDomainCode(String domaincode);

	FormDomainVO create(FormDomainVO vo) throws Exception;

	List<CtrlDimSource> createCtrlDimSource(List<CtrlDimSourceOptionVO> vos, String formdomainid) throws Exception;
	
	CtrlDimSource createCtrlDimSource(CtrlDimSourceOptionVO vo, String formdomainid) throws Exception;
	
	FormDomainVO modify(FormDomainVO vo) throws Exception;
	
	/**
	 * 删除数据
	 * @param formdomainid 待删除的FormDomain数据主键ID
	 * @param mode 删除模式：逻辑删除 | 物理删除
	 * @return 删除结果：true - 成功；false - 失败。
	 * @throws Exception
	 */
	boolean delete(String formdomainid, DeleteMode mode) throws Exception;
	
	/**
	 * 逻辑删除（执行update操作，将FormDomain数据更新为已删除状态）
	 * @param formdomainid 待删除的FormDomain数据主键ID
	 * @return 删除结果：true - 成功；false - 失败。
	 * @throws Exception
	 */
	boolean deleteOnLogic(String formdomainid) throws Exception;

	/**
	 * 物理删除（执行delete操作，将FormDomain数据从数据库删除）
	 * @param formdomainid 待删除的FormDomain数据主键ID
	 * @return 删除结果：true - 成功；false - 失败。
	 * @throws Exception
	 */
	boolean deleteOnPhysical(String formdomainid) throws Exception;
}
