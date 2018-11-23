package com.zyc.form.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.data.EmptyNodeType;
import com.zyc.form.service.FormService;
import com.zyc.form.serviceassist.FieldServiceAssistor;
import com.zyc.form.serviceassist.FormServiceAssistor;

@Controller
public class FormDesignMgrController {

	private static final String commonPath = "/mgr/form/design";

	@Autowired
	private FormService formService;
	
	@Autowired
	private FormServiceAssistor formServiceAssistor;
	
	@Autowired
	private FieldServiceAssistor fieldServiceAssistor;
	
    @RequestMapping(value = commonPath, method = RequestMethod.GET)
	public String index(Model model) throws Exception {
    	this.handleIndex(model, null);
    	return commonPath + "/index";
	}
    
    @RequestMapping(value = commonPath + "/{formid}", method = RequestMethod.GET)
	public String index(Model model, @PathVariable(name = "formid") String formid) throws Exception {
    	this.handleIndex(model, formid);
    	return commonPath + "/index";
	}
    
    private void handleIndex(Model model, String formid) throws Exception {
    	boolean hasFormid = StringUtils.isNotBlank(formid);
    	model.addAttribute("currentFormid", hasFormid ? formid : "");
    	model.addAttribute("formTree", this.formServiceAssistor.composeFormTree(EmptyNodeType.OPTIONAL));
    	model.addAttribute("areaFieldTree", hasFormid ? this.fieldServiceAssistor.composeAreaFieldTree(formid, null) : null);
    	model.addAttribute("areas", this.formServiceAssistor.formAreas(formid, EmptyNodeType.OPTIONAL));
    	model.addAttribute("emptyNodeType", this.formService.enumToMap(EmptyNodeType.class));
    }

    @RequestMapping(value = commonPath + "/spreadtest", method = RequestMethod.GET)
    public String spreadtest(Model model) {
    	return commonPath + "/spreadtest";
    }
}
