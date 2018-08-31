package com.zyc.form.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BudgetAPIWebCaller extends BaseFormController {
	
	private static final String CALLER_PATH = "/budget/api";
	 
	@RequestMapping(value = CALLER_PATH + "/budgetTypes", method = RequestMethod.GET)
	public String budgetTypes() {
		return this.budgetClient.budgetTypes();
	}
}
