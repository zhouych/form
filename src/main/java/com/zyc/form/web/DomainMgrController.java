package com.zyc.form.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zyc.baselibs.entities.DataStatus;
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
		return mgrPath + "/index";
	}

    @RequestMapping(value = mgrPath + "/addpage", method = RequestMethod.GET)
    public String addpage(Model model) {
    	this.handleDetailRequest(model, ClientAction.ADD, null, false);
    	return mgrPath + "/detail";
    }

    @RequestMapping(value = mgrPath + "/add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute("domain") FormDomainVO domain) throws Exception {
    	domain = this.formDomainService.create(domain);
    	return "redirect:" + mgrPath + "/editpage/" + domain.getId();
    }

    @RequestMapping(value = mgrPath + "/editpage/{formdomainid}", method = RequestMethod.GET)
    public String editpage(Model model, @PathVariable(name = "formdomainid") String formdomainid) {
    	this.handleDetailRequest(model, ClientAction.EDIT, formdomainid, false);
    	return mgrPath + "/detail";
    }

    @RequestMapping(value = mgrPath + "/edit", method = RequestMethod.POST)
    public String edit(FormDomainVO domain) throws Exception {
    	domain = this.formDomainService.modify(domain);
    	return "redirect:" + mgrPath + "/editpage/" + domain.getId();
    }
    
    private String handleDetailRequest(Model model, ClientAction action, String formdomainid, boolean readonly) {
    	model.addAttribute("action", action.getValue());
    	model.addAttribute("actionText", action.getText());
    	model.addAttribute("allDataStatus", DataStatus.toList());
    	
    	FormDomainVO domain = null;
    	if(action == ClientAction.ADD) {
    		domain = FormDomainVO.newInstance();
    	} else if(action == ClientAction.EDIT) {
    		if(model.containsAttribute("domain")) {
    			domain = (FormDomainVO) model.asMap().get("domain");
    		}
    		if(domain == null) {
        		domain = this.formDomainService.selectByFormDomainId(formdomainid);
    		}
    	}
    	domain.addCtrlDimSourceOptions(this.mdataClient.budgetCtrlDimensions());
    	
    	model.addAttribute("domain", domain);
    	model.addAttribute("readonly", readonly || !DataStatus.ENABLED.getValue().equals(domain.getDatastatus()));
    	
    	return mgrPath + "/detail";
    }
}
