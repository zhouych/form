package com.zyc.form.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import com.zyc.baselibs.web.BaseController;
import com.zyc.form.client.BudgetClient;
import com.zyc.form.client.MdataClient;

public abstract class BaseFormController extends BaseController {

	@Autowired
	protected DiscoveryClient discoveryClient;
	
	@Autowired
	protected BudgetClient budgetClient;
	
	@Autowired
	protected MdataClient mdataClient; 
	
}
