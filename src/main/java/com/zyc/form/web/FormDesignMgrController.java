package com.zyc.form.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zyc.baselibs.web.bootstrap.EmptyNodeType;
import com.zyc.form.webadvisory.FormIndustryAdvisor;

@Controller
public class FormDesignMgrController {

	private static final String commonPath = "/mgr/form/design";
	
	@Autowired
	private FormIndustryAdvisor formIndustryAdvisor;

    @RequestMapping(value = commonPath, method = RequestMethod.GET)
	public String index(Model model) {
    	model.addAttribute("formTree", this.formIndustryAdvisor.composeFormTree(EmptyNodeType.OPTIONAL));
    	return commonPath + "/index";
	}
    
    @RequestMapping(value = commonPath + "/{formid}", method = RequestMethod.GET)
	public String index(Model model, @PathVariable(name = "formid") String formid) {
    	model.addAttribute("formTree", this.formIndustryAdvisor.composeFormTree(EmptyNodeType.OPTIONAL));
    	return commonPath + "/index";
	}
}
