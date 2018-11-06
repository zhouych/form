package com.zyc.form.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zyc.baselibs.web.bootstrap.BsTableQueryParameter;
import com.zyc.form.serviceassist.FieldServiceAssistor;
import com.zyc.form.vo.FormFieldVO;

@Controller
public class FormFieldMgrController extends BaseFormController {
	
	private static final Logger logger = Logger.getLogger(FormFieldMgrController.class);

	private static final String commonPath = "/mgr/form/field";
	
	@Autowired
	private FieldServiceAssistor fieldServiceAssistor;

    @Override
    protected String getCommonPath() {
    	return commonPath;
    }
    
    @RequestMapping(value = commonPath + "/{formid}", method = RequestMethod.GET)
	public String index(Model model, @PathVariable(name = "formid") String formid) throws Exception {
    	model.addAttribute("formid", formid);
    	return commonPath + "/index";
	}
    
    @RequestMapping(value = commonPath + "/fields", method = RequestMethod.POST)
    public @ResponseBody String fields(@RequestBody BsTableQueryParameter<FormFieldVO> param) {
    	String json = JSON.toJSONString(this.fieldServiceAssistor.composeFormFieldBsTableDataSource(param.getCondition(), param.getSearchText(), param.getPagination()));
    	logger.debug("[fields(...)] - JSON data = " + json);
		return json;
    }
}
