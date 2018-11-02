package com.zyc.form.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.baselibs.web.ClientAction;
import com.zyc.baselibs.web.EmptyNodeType;
import com.zyc.baselibs.web.bootstrap.BsTableQueryParameter;
import com.zyc.form.data.FormType;
import com.zyc.form.service.FormService;
import com.zyc.form.serviceassist.FormDomainServiceAssistor;
import com.zyc.form.serviceassist.FormServiceAssistor;
import com.zyc.form.vo.FormVO;

@Controller
public class FormMgrController extends BaseFormController {
	
	private static final Logger logger = Logger.getLogger(FormMgrController.class);

	private static final String commonPath = "/mgr/form";

	@Autowired
	private FormDomainServiceAssistor domainServiceAssistor;
	
	@Autowired
	private FormService formService;
	
	@Autowired
	private FormServiceAssistor formServiceAssistor;
	
	//@Autowired
	//private AreaFieldServiceAssistor areaFieldServiceAssistor;

    @Override
    protected String getCommonPath() {
    	return commonPath;
    }
    
    @RequestMapping(value = commonPath, method = RequestMethod.GET)
	public String index(Model model) throws Exception {
    	return commonPath + "/index";
	}

    @RequestMapping(value = commonPath + "/addpage", method = RequestMethod.GET)
    public String addpage(Model model) {
    	this.requestDetail(model, ClientAction.ADD, null, false, null);
    	return this.getDetailViewUrl();
    }

    @RequestMapping(value = commonPath + "/add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute("form") FormVO form) throws Exception {
    	form = this.formService.create(form);
    	return this.getRedirectEditpageUrl(form.getId());
    }
    
    @RequestMapping(value = commonPath + "/editpage/{formid}", method = RequestMethod.GET)
    public String editpage(Model model, @PathVariable(name = "formid") String formid) {
    	this.requestDetail(model, ClientAction.EDIT, formid, false, null);
    	return this.getDetailViewUrl();
    }

    @RequestMapping(value = commonPath + "/edit", method = RequestMethod.POST)
    public String edit(FormVO form) throws Exception {
    	form = this.formService.modify(form);
    	return this.getRedirectEditpageUrl(form.getId());
    }
    
    @Override
    protected <T extends BaseEntity> String requestDetail(Model model, ClientAction action, String formid, boolean readonly, T entity) {
    	FormVO form = (FormVO) entity;
    	boolean whetherView = action == ClientAction.VIEW;
    	if(form == null) {
        	if(action == ClientAction.ADD) {
        		form = FormVO.newInstance();
        		form.setFormdomainid(this.getRequest().getParameter("formdomainid")); //针对在指定表单域下新增表单
        	} else if(action == ClientAction.EDIT || whetherView) {
        		if(model.containsAttribute("form")) {
        			form = (FormVO) model.asMap().get("form");
        		}
        		if(form == null) {
            		form = this.formService.selectByFormid(formid);
        		}
        	}
    	}
    	model.addAttribute("form", form);
    	model.addAttribute("hselectDomains", this.domainServiceAssistor.composeDomains(EmptyNodeType.NONE));
    	model.addAttribute("formTypes", FormType.toList(EmptyNodeType.NONE));
    	return super.requestDetail(model, action, formid, readonly, form);
    }

    @RequestMapping(value = commonPath + "/forms", method = RequestMethod.POST)
    public @ResponseBody String domains(@RequestBody BsTableQueryParameter<FormVO> param) {
    	String json = JSON.toJSONString(this.formServiceAssistor.composeBsTableDataSource(param.getCondition(), param.getSearchText(), param.getPagination()));
    	logger.debug("[FormMgrController.domains(...)] - JSON data = " + json);
		return json;
    }
}
