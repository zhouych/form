
package com.zyc.form.service;

import java.util.List;

import com.zyc.baselibs.vo.DeleteMode;
import com.zyc.form.vo.FormDomainVO;

public interface FormDomainService {

	List<FormDomainVO> selectAll(); 
	
	FormDomainVO selectByFormDomainId(String formdomainid);
	
	FormDomainVO selectByFormDomainCode(String domaincode);

	FormDomainVO create(FormDomainVO vo) throws Exception;
	
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
