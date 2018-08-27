package com.zyc.form.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.zyc.baselibs.web.BaseController;
import com.zyc.baselibs.web.ResponseResult;
import com.zyc.form.data.FormType;

@RestController
public class FormTypeController extends BaseController {
	
	private static final Logger logger = LogManager.getLogger(FormTypeController.class);
	
	@Autowired
	private DiscoveryClient client;

    @RequestMapping(value = "/types" ,method = RequestMethod.GET)
    public String types() {
		ResponseResult result = new ResponseResult();
		
		result.setData(FormType.toMap());
		
		logger.debug("client=" + client);
		
		logger.debug("[FormTypeController.types()] - ......");
		
		return JSON.toJSONString(result);
    }
}
