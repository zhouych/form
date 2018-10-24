package com.zyc.form.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.zyc.baselibs.web.ResponseResult;
import com.zyc.baselibs.web.bootstrap.BootstrapTableDataSource;
import com.zyc.form.service.FormDomainService;
import com.zyc.form.vo.FormDomainVO;

@RestController
public class DomainRestController extends BaseFormController {
	
	private static final Logger logger = Logger.getLogger(DomainRestController.class);

	@Autowired
	private FormDomainService formDomainService;
    
    private static final String restPath = "/api";
    
    @RequestMapping(value = restPath + "/domains", method = RequestMethod.GET)
    public String domains() {
		BootstrapTableDataSource<FormDomainVO> dataSource = new BootstrapTableDataSource<FormDomainVO>();
		
		try {
			List<FormDomainVO> rows = this.formDomainService.selectAll();
			dataSource.setRows(rows);
			dataSource.setTotal(rows == null ? 0 : rows.size());
		} catch (Exception e) {
			this.handleException(new ResponseResult(), e, logger);
		}
		
		return JSON.toJSONString(dataSource);
    }
}
