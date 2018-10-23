package com.zyc.form.web;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.zyc.baselibs.web.ResponseResult;

@RestController
public class MdataAPIWebCaller extends BaseFormController {
	
	private static final Logger logger = Logger.getLogger(MdataAPIWebCaller.class);

	private static final String CALLER_PATH = "/mdata/api";

	@RequestMapping(value = CALLER_PATH + "/dimensionMembers", method = RequestMethod.GET)
	public String dimensionMembers(String dimensionCode, String parentId) {
		ResponseResult result = new ResponseResult();
		
		try {
			result.setData(this.mdataClient.dimensionMembers(dimensionCode, parentId));
		} catch (Exception e) {
			this.handleException(result, e, logger);
		}
		
		return JSON.toJSONString(result);
	}
}
