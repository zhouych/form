package com.zyc.form.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.baselibs.web.ClientAction;
import com.zyc.form.service.FormDomainService;
import com.zyc.form.vo.FormDomainVO;

@Controller
public class DomainMgrController extends BaseFormController {
	
	private static final String commonPath = "/mgr/domain";
	
	@Autowired
	private FormDomainService formDomainService;
	
	@Override
	protected String getCommonPath() {
		return commonPath;
	}
	
    @RequestMapping(value = commonPath, method = RequestMethod.GET)
	public String index(Model model) {
		return commonPath + "/index";
	}

    @RequestMapping(value = commonPath + "/view/{formdomainid}", method = RequestMethod.GET)
    public String view(Model model, @PathVariable(name = "formdomainid") String formdomainid) {
    	this.requestDetail(model, ClientAction.VIEW, formdomainid, false, null);
    	return this.getDetailUrl();
    }

    @RequestMapping(value = commonPath + "/addpage", method = RequestMethod.GET)
    public String addpage(Model model) {
    	this.requestDetail(model, ClientAction.ADD, null, false, null);
    	return this.getDetailUrl();
    }

    @RequestMapping(value = commonPath + "/add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute("domain") FormDomainVO domain) throws Exception {
    	domain = this.formDomainService.create(domain);
    	return this.getRedirectEditpageUrl(domain.getId());
    }

    @RequestMapping(value = commonPath + "/editpage/{formdomainid}", method = RequestMethod.GET)
    public String editpage(Model model, @PathVariable(name = "formdomainid") String formdomainid) {
    	this.requestDetail(model, ClientAction.EDIT, formdomainid, false, null);
    	return this.getDetailUrl();
    }

    @RequestMapping(value = commonPath + "/edit", method = RequestMethod.POST)
    public String edit(FormDomainVO domain) throws Exception {
    	domain = this.formDomainService.modify(domain);
    	return this.getRedirectEditpageUrl(domain.getId());
    }
    
    @Override
    protected <T extends BaseEntity> String requestDetail(Model model, ClientAction action, String formdomainid, boolean readonly, T entity) {
    	FormDomainVO domain = (FormDomainVO) entity;
    	boolean whetherView = action == ClientAction.VIEW;
    	if(domain == null) {
    		if(action == ClientAction.ADD) {
        		domain = FormDomainVO.newInstance();
        	} else if(action == ClientAction.EDIT || whetherView) {
        		if(model.containsAttribute("domain")) {
        			domain = (FormDomainVO) model.asMap().get("domain");
        		}
        		if(domain == null) {
            		domain = this.formDomainService.selectByFormDomainId(formdomainid);
        		}
        	}
        	domain.addCtrlDimSourceOptions(this.mdataClient.budgetCtrlDimensions());
        	
    	} 
    	model.addAttribute("domain", domain);
    	return super.requestDetail(model, action, formdomainid, readonly || whetherView, domain);
    }
}
