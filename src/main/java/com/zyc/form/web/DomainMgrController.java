package com.zyc.form.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zyc.baselibs.db.mysql.MysqlScriptComponent;
import com.zyc.baselibs.entities.DataStatus;
import com.zyc.baselibs.web.ClientAction;
import com.zyc.form.entities.CtrlDimSource;
import com.zyc.form.service.FormDomainService;
import com.zyc.form.vo.FormDomainVO;

@Controller
public class DomainMgrController extends BaseFormController {
	
	private static final String mgrPath = "/mgr/domain";
	
	@Autowired
	private FormDomainService formDomainService;
	
	@Autowired
	private MysqlScriptComponent mysqlScriptComponent;
	
    @RequestMapping(value = mgrPath, method = RequestMethod.GET)
	public String index(Model model) {
    	model.addAttribute("domains", this.formDomainService.selectAll());
		return mgrPath + "/index";
	}

    @RequestMapping(value = mgrPath + "/addpage", method = RequestMethod.GET)
    public String addpage(Model model) {
    	this.handleDetailRequest(model, ClientAction.ADD, null);
    	
    	List<String> sqlscripts = mysqlScriptComponent.entity2tableSqlScripts("com.zyc.form.entities");
    	if(sqlscripts != null && !sqlscripts.isEmpty()) {
    		for (String sqlscript : sqlscripts) {
				System.out.println(sqlscript);
			}
    	}
    	
    	return mgrPath + "/detail";
    }

    @RequestMapping(value = mgrPath + "/add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute("domain") FormDomainVO domain) {
    	
    	return mgrPath + "/detail";
    }

    @RequestMapping(value = mgrPath + "/editpage/{formdomainid}", method = RequestMethod.GET)
    public String editpage(Model model, @PathVariable(name = "formdomainid") String formdomainid) {
    	this.handleDetailRequest(model, ClientAction.EDIT, formdomainid);
    	return mgrPath + "/detail";
    }
    
    private String handleDetailRequest(Model model, ClientAction action, String formdomainid) {
    	model.addAttribute("action", action.getValue());
    	model.addAttribute("actionText", action.getText());
    	model.addAttribute("allDataStatus", DataStatus.toList());
    	
    	FormDomainVO domain = null;
    	if(action == ClientAction.ADD) {
    		domain = FormDomainVO.newInstance();
    	} else if(action == ClientAction.EDIT) {
    		domain = this.formDomainService.selectByFormDomainId(formdomainid);
    	}
    	
    	domain.setCtrlDimSources(new ArrayList<CtrlDimSource>());
    	CtrlDimSource cds = new CtrlDimSource();
		BeanUtils.copyProperties(this.mdataClient.budgetCtrlDimensions().get(0), cds);
		cds.setExpression("+@2(" + cds.getDimcode() + ");");
		cds.setExpressiontext("包含@成员及后代（" + cds.getDimcode() + "）");
    	domain.getCtrlDimSources().add(cds);
    	
    	domain.setCtrlDimUIOptionVos(this.mdataClient.budgetCtrlDimensions());
    	
    	model.addAttribute("domain", domain);
    	
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
