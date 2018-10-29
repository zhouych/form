package com.zyc.form.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zyc.baselibs.entities.DataStatus;
import com.zyc.baselibs.web.ClientAction;
import com.zyc.baselibs.web.bootstrap.BsTableQueryParameter;
import com.zyc.form.service.FormService;
import com.zyc.form.serviceassist.FormServiceAssistor;
import com.zyc.form.vo.FormVO;

@Controller
public class FormMgrController extends BaseFormController {
	
	private static final Logger logger = Logger.getLogger(FormMgrController.class);

	private static final String commonPath = "/mgr/form";

	@Autowired
	private FormService formService;
	
	@Autowired
	private FormServiceAssistor formServiceAssistor;
	
	//@Autowired
	//private AreaFieldServiceAssistor areaFieldServiceAssistor;

    @RequestMapping(value = commonPath, method = RequestMethod.GET)
	public String index(Model model) throws Exception {
    	return commonPath + "/index";
	}

    @RequestMapping(value = commonPath + "/addpage", method = RequestMethod.GET)
    public String addpage(Model model) {
    	this.handleDetailRequest(model, ClientAction.ADD, null, false);
    	return commonPath + "/detail";
    }

    @RequestMapping(value = commonPath + "/add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute("form") FormVO form) throws Exception {
    	form = this.formService.create(form);
    	return "redirect:" + commonPath + "/editpage/" + form.getId();
    }
    
    private String handleDetailRequest(Model model, ClientAction action, String formid, boolean readonly) {
    	model.addAttribute("action", action.getValue());
    	model.addAttribute("actionText", action.getText());
    	model.addAttribute("allDataStatus", DataStatus.toList());
    	
    	FormVO form = null;
    	if(action == ClientAction.ADD) {
    		form = FormVO.newInstance();
    	} else if(action == ClientAction.EDIT) {
    		if(model.containsAttribute("domain")) {
    			form = (FormVO) model.asMap().get("form");
    		}
    		if(form == null) {
        		form = this.formService.selectByFormid(formid);
    		}
    	}
    	
    	model.addAttribute("form", form);
    	model.addAttribute("readonly", readonly || !DataStatus.ENABLED.getValue().equals(form.getDatastatus()));
    	
    	return commonPath + "/detail";
    }

    @RequestMapping(value = commonPath + "/forms", method = RequestMethod.POST)
    public @ResponseBody String domains(@RequestBody BsTableQueryParameter<FormVO> param) {
    	String json = JSON.toJSONString(this.formServiceAssistor.composeBsTableDataSource(param.getCondition(), param.getSearchText(), param.getPagination()));
    	logger.debug("[FormMgrController.domains(...)] - JSON data = " + json);
		return json;
    }
}
