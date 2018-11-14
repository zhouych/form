package com.zyc.form.serviceassist.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.data.EmptyNodeType;
import com.zyc.baselibs.ex.BussinessException;
import com.zyc.baselibs.vo.EntryBean;
import com.zyc.baselibs.vo.Pagination;
import com.zyc.baselibs.vo.PaginationResult;
import com.zyc.baselibs.web.bootstrap.BsTableDataSource;
import com.zyc.baselibs.web.bootstrap.TreeViewNode;
import com.zyc.baselibs.web.bootstrap.TreeViewNodeState;
import com.zyc.form.data.FieldTreeNodeType;
import com.zyc.form.data.FormArea;
import com.zyc.form.data.FormType;
import com.zyc.form.service.FormFieldService;
import com.zyc.form.service.FormService;
import com.zyc.form.serviceassist.FieldServiceAssistor;
import com.zyc.form.vo.FormFieldVO;
import com.zyc.form.vo.FormVO;

@Service
public class FieldServiceAssistorImpl implements FieldServiceAssistor {
	
	@Autowired
	private FormService formService;

	@Autowired
	private FormFieldService formFieldService;
	
	@Override
	public List<TreeViewNode> composeAreaFieldTree(String formid, EmptyNodeType emptyNodeType) throws BussinessException {
		FormVO formvo = this.formService.selectByFormid(formid);
		if(null == formvo) {
			throw new BussinessException("找不到表单[formid=" + formid + "]。");
		}
		
		FormType formtype = FormType.from(formvo.getFormtype());
		
		List<TreeViewNode> nodes = new ArrayList<TreeViewNode>();
		TreeViewNode areaNode = null;
		for (EntryBean area : FormArea.toList(formtype)) {
			areaNode = new TreeViewNode();
			areaNode.setText(area.label());
			areaNode.getAttrs().put("value", area.getValue());
			areaNode.getAttrs().put("text", area.getText());
			areaNode.getAttrs().put("nodeType", FieldTreeNodeType.AREA.getValue());
			nodes.add(areaNode);
		}

		List<FormFieldVO> fieldvos = this.formFieldService.selectFormFieldByFormid(formid);
		if(fieldvos == null || fieldvos.isEmpty()) {
			throw new BussinessException("找不到表单[formid=" + formid + "]对应的字段。");
		}
		
		for (FormFieldVO fieldvo : fieldvos) {
			//如果区域（areaNode）存在，且是当前字段（fieldvo）属于该区域（areaNode），则不需要再寻找字段（fieldvo）的所属区域（areaNode）。
			//基于以上，反之，则需要寻找字段（fieldvo）的所属区域（areaNode）。具体逻辑如下代码所示：
			if(areaNode == null || !fieldvo.getFormarea().equals(areaNode.getAttrs().get("value"))) {
				areaNode = null;
				for (int i = 0; i < nodes.size(); i++) {
					if(fieldvo.getFormarea().equals(nodes.get(i).getAttrs().get("value"))) {
						areaNode = nodes.get(i);
						break;
					}
				}	
			}
			
			if(areaNode != null) {
				TreeViewNode fieldNode = new TreeViewNode();
				fieldNode.setText(fieldvo.label());
				//通过反射将字段（fieldvo）的所有属性值传给字段节点（fieldNode）
				ReflectUtils.scanFields(FormFieldVO.class, new Visitor<Field, Boolean>() {
					public Boolean visit(Field field) {
						Object value = ReflectUtils.getValue(field, fieldvo);
						fieldNode.getAttrs().put(field.getName(), value == null ? "" : value) ;
						return false;
					}
				}, false, ReflectUtils.MODIFIER_STATIC$FINAL);
				fieldNode.getAttrs().put("nodeType", FieldTreeNodeType.FIELD.getValue());
				
				if(areaNode.getState() == null) {
					areaNode.setState(new TreeViewNodeState());	
				}
				
				if(areaNode.getNodes() == null) {
					areaNode.setNodes(new ArrayList<TreeViewNode>());	
				}
				areaNode.getNodes().add(fieldNode);
			}
		}
		
		return nodes;
	}

	@Override
	public BsTableDataSource<FormFieldVO> composeFormFieldBsTableDataSource(FormFieldVO condition, String searchText, Pagination pagination) {
		PaginationResult<FormFieldVO> result = this.formFieldService.selectByPage(condition, searchText, pagination);
		BsTableDataSource<FormFieldVO> dataSource = new BsTableDataSource<FormFieldVO>();
		dataSource.setRows(result.getRows());
		dataSource.setTotal(result.getTotal());
		return dataSource;
	}

}
