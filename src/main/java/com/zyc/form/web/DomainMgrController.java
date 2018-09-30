package com.zyc.form.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zyc.baselibs.web.ClientAction;
import com.zyc.form.service.FormDomainService;
import com.zyc.form.vo.FormDomainVO;

@Controller
public class DomainMgrController extends BaseFormController {
	
	private static final String mgrPath = "/mgr/domain";
	
	@Autowired
	private FormDomainService formDomainService;
	
    @RequestMapping(value = mgrPath, method = RequestMethod.GET)
	public String index(Model model) {
    	model.addAttribute("domains", this.formDomainService.selectAll());
		return mgrPath + "/index";
	}

    @RequestMapping(value = mgrPath + "/addpage", method = RequestMethod.GET)
    public String addpage(Model model) {
    	model.addAttribute("action", ClientAction.ADD.getValue());
    	model.addAttribute("actionText", ClientAction.ADD.getText());
    	return mgrPath + "/detail";
    }

    @RequestMapping(value = mgrPath + "/add", method = RequestMethod.POST)
    public String add(FormDomainVO domain) {
    	return null;
    }

    @RequestMapping(value = mgrPath + "/editpage/{formdomainid}", method = RequestMethod.GET)
    public String editpage(Model model, @PathVariable(name = "formdomainid") String formdomainid) {
    	model.addAttribute("action", ClientAction.EDIT.getValue());
    	model.addAttribute("actionText", ClientAction.EDIT.getText());
    	model.addAttribute("domain", this.formDomainService.selectByFormDomainId(formdomainid));
    	return mgrPath + "/detail";
    }

    @RequestMapping(value = mgrPath + "/edit", method = RequestMethod.POST)
    public String edit(FormDomainVO domain) {
    	return null;
    }
    
    private static final String restPath = "/form/api";
    
    @ResponseBody
    @RequestMapping(value = restPath + "/domains", method = RequestMethod.GET)
    public String domains() {
    	List<FormDomainVO> domains = this.formDomainService.selectAll();
    	return JSON.toJSONString(domains);
    }
}
