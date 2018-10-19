package com.zyc.form.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MdataAPIWebCaller extends BaseFormController {

	private static final String CALLER_PATH = "/mdata/api";
	

	@RequestMapping(value = CALLER_PATH + "/dimensionMembers", method = RequestMethod.GET)
	public String dimensionMembers() {
		return "";
	}
}
