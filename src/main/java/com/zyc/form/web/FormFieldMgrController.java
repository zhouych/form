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
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.baselibs.web.ClientAction;
import com.zyc.baselibs.web.EmptyNodeType;
import com.zyc.baselibs.web.bootstrap.BsTableQueryParameter;
import com.zyc.form.data.FieldDataType;
import com.zyc.form.data.FieldDisplayType;
import com.zyc.form.data.FormArea;
import com.zyc.form.data.FormType;
import com.zyc.form.entities.Form;
import com.zyc.form.service.FormFieldService;
import com.zyc.form.service.FormService;
import com.zyc.form.serviceassist.FieldServiceAssistor;
import com.zyc.form.vo.FormFieldVO;

@Controller
public class FormFieldMgrController extends BaseFormController {
	
	private static final Logger logger = Logger.getLogger(FormFieldMgrController.class);

	private static final String commonPath = "/mgr/form/field";
	
	private static final String FIELD = "field";
	
	private static final String FORM = "form";
	
	private static final String FORMID = "formid";
	
	@Autowired
	private FieldServiceAssistor fieldServiceAssistor;

	@Autowired
	private FormService formService;

	@Autowired
	private FormFieldService formFieldService;

    @Override
    protected String getCommonPath() {
    	return commonPath;
    }
    
    @Override
    protected String getDetailReturnUrlDefaults() {
    	return super.getDetailReturnUrlDefaults() + "/" + this.getRequest().getAttribute(FORMID);
    }
    
    /**
     * Request-View: 进入指定表单的字段列表视图
     */
    @RequestMapping(value = commonPath + "/{formid}", method = RequestMethod.GET)
	public String index(Model model, @PathVariable(name = FORMID) String formid) throws Exception {
    	this.attributeFormAbouts(model, formid);
    	this.attributeFieldAbouts(model);
    	this.attributeReturnUrl(model);
    	return commonPath + "/index";
	}

    /**
     * Request-View: 进入指定表单的字段新增视图
     */
    @RequestMapping(value = commonPath + "/addpage", method = RequestMethod.GET)
    public String addpage(Model model) {
    	this.requestDetail(model, ClientAction.ADD, null, false, null);
    	return this.getDetailUrl();
    }

    /**
     * Request-View: 新增表单字段
     */
    @RequestMapping(value = commonPath + "/add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute(FIELD) FormFieldVO field) throws Exception {
    	field = this.formFieldService.create(field);
    	return this.getRedirectEditpageUrl(field.getId());
    }

    /**
     * Request-View: 进入指定表单的字段编辑视图
     */
    @RequestMapping(value = commonPath + "/editpage/{formfieldid}", method = RequestMethod.GET)
    public String editpage(Model model, @PathVariable(name = "formfieldid") String formfieldid) {
    	this.requestDetail(model, ClientAction.EDIT, formfieldid, false, null);
    	return this.getDetailUrl();
    }

    /**
     * Request-View: 编辑表单字段
     */
    @RequestMapping(value = commonPath + "/edit", method = RequestMethod.POST)
    public String edit(FormFieldVO field) throws Exception {
    	field = this.formFieldService.modify(field);
    	return this.getRedirectEditpageUrl(field.getId());
    }
    
    @Override
    protected <T extends BaseEntity> String requestDetail(Model model, ClientAction action, String formfieldid, boolean readonly, T entity) {
    	FormFieldVO field = (FormFieldVO) entity;
    	boolean whetherView = action == ClientAction.VIEW;
    	if(field == null) {
        	if(action == ClientAction.ADD) {
        		field = FormFieldVO.newInstance();
        		field.setFormid(this.getRequest().getParameter(FORMID)); //针对在指定表单下新增字段
        	} else if(action == ClientAction.EDIT || whetherView) {
        		if(model.containsAttribute(FIELD)) {
        			field = (FormFieldVO) model.asMap().get(FORM);
        		}
        		if(field == null) {
            		field = this.formFieldService.selectByFormfieldid(formfieldid);
        		}
        	}
    	}
    	model.addAttribute(FIELD, field);
    	this.attributeFormAbouts(model, field.getFormid());
    	this.attributeFieldAbouts(model);
    	this.getRequest().setAttribute(FORMID, field.getFormid());
    	return super.requestDetail(model, action, formfieldid, readonly, field);
    }
    
    private void attributeFormAbouts(Model model, String formid) {
    	FormType formtype = null;
    	if(StringUtils.isNotBlank(formid)) {
    		Form form = this.formService.selectByFormid(formid);
        	model.addAttribute(FORM, form);
        	formtype = StringUtils.toEnumIgnoreCase(FormType.class, form.getFormtype());
    	}
    	model.addAttribute("formAreas", FormArea.toList(formtype));
    }
    
    private void attributeFieldAbouts(Model model) {
    	model.addAttribute("dataTypes", FieldDataType.toList(EmptyNodeType.OPTIONAL));
    	model.addAttribute("displayTypes", FieldDisplayType.toList(EmptyNodeType.OPTIONAL));
    }
    
    /**
     * Request-Data: 为bootstrap-table表格插件而分页加载表单字段数据
     */
    @RequestMapping(value = commonPath + "/fields", method = RequestMethod.POST)
    public @ResponseBody String fields(@RequestBody BsTableQueryParameter<FormFieldVO> param) {
    	String json = JSON.toJSONString(this.fieldServiceAssistor.composeFormFieldBsTableDataSource(param.getCondition(), param.getSearchText(), param.getPagination()));
    	logger.debug("[fields(...)] - JSON data = " + json);
		return json;
    }
}
