package com.zyc.form.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.zyc.baselibs.web.ResponseResult;
import com.zyc.form.service.FormFieldService;

@RestController
public class FormFieldRestController extends BaseFormController {
	
	private static final Logger logger = Logger.getLogger(FormFieldRestController.class);

	private static final String commonPath = "/api/form/field";
	
	@Autowired
	private FormFieldService formFieldService;

    @Override
    protected String getCommonPath() {
    	return commonPath;
    }
    
    @RequestMapping(value = commonPath + "/refresh/{formid}", method = RequestMethod.POST)
	public String refresh(@PathVariable(name = "formid") String formid) throws Exception {
    	ResponseResult result = new ResponseResult();
    	try {
    		boolean success = this.formFieldService.refresh(formid);
			result.setStatus(success ? "0" : "1");
			result.setMessage(success ? "刷新成功！" : "刷新失败！");
		} catch (Exception e) {
			this.handleException(result, e, logger);
		}
    	return JSON.toJSONString(result);
	}

    @RequestMapping(value = commonPath + "/apply", method = RequestMethod.GET)
    public String apply(@RequestParam("formid") String formid, @RequestParam("formarea") String formarea) {
    	ResponseResult result = new ResponseResult();
    	try {
    		result.setData(formFieldService.applyItemField(formid, formarea));
		} catch (Exception e) {
			this.handleException(result, e, logger);
		}
    	return JSON.toJSONString(result);
    }

    @RequestMapping(value = commonPath + "/value/apply", method = RequestMethod.GET)
    public String applyValue(@RequestParam("formid") String formid, @RequestParam("formarea") String formarea) {
    	ResponseResult result = new ResponseResult();
    	try {
    		result.setData(formFieldService.applyItemFieldValue(formid, formarea));
		} catch (Exception e) {
			this.handleException(result, e, logger);
		}
    	return JSON.toJSONString(result);
    }
}
