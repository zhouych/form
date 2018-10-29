package com.zyc.form.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.zyc.baselibs.vo.DeleteMode;
import com.zyc.baselibs.web.ResponseResult;
import com.zyc.baselibs.web.bootstrap.BsTableDataSource;
import com.zyc.baselibs.web.bootstrap.BsTableQueryParameter;
import com.zyc.form.service.FormDomainService;
import com.zyc.form.vo.FormDomainQueryVO;
import com.zyc.form.vo.FormDomainVO;

@RestController
public class DomainRestController extends BaseFormController {
	
	private static final Logger logger = Logger.getLogger(DomainRestController.class);

	@Autowired
	private FormDomainService formDomainService;
    
    @RequestMapping(value = restPath + "/domain/{formdomainid}", method = RequestMethod.DELETE)
    public String domain(@PathVariable(name = "formdomainid") String formdomainid) {
    	ResponseResult result = new ResponseResult();
    	try {
    		boolean success = this.formDomainService.delete(formdomainid, DeleteMode.LOGIC);
			result.setStatus(success ? "0" : "1");
			result.setMessage(success ? "删除成功！" : "删除失败！");
		} catch (Exception e) {
			this.handleException(result, e, logger);
		}
    	return JSON.toJSONString(result);
    }
    
    @RequestMapping(value = restPath + "/domains", method = RequestMethod.POST)
    public String domains(@RequestBody BsTableQueryParameter<FormDomainQueryVO> param) {
		List<FormDomainVO> rows = this.formDomainService.selectByPage(param.getCondition(), param.getSearchText(), param.getPagination());
		BsTableDataSource<FormDomainVO> dataSource = new BsTableDataSource<FormDomainVO>();
		dataSource.setRows(rows);
		dataSource.setTotal(rows == null ? 0 : rows.size());		
		return JSON.toJSONString(dataSource);
    }
}
