package com.zyc.form.client.ribbon;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.web.bootstrap.TreeViewNodeForLazyLoad;
import com.zyc.baselibs.web.bootstrap.TreeViewNodeState;
import com.zyc.form.client.MdataClient;
import com.zyc.form.client.vo.DimensionVO;
import com.zyc.form.client.vo.MemberVO;

/**
 * 基于ribbon的支持负载均衡的负责与主数据微服务（Mdata）进行交互的客户端
 * @author zhouyancheng
 *
 */
@Service
public class MdataClientRibbonImpl implements MdataClient {
	
	static final List<DimensionVO> budget_ctrl_dims = new ArrayList<DimensionVO>();
	
	static final List<MemberVO> members = new ArrayList<MemberVO>();
	
	static {
		budget_ctrl_dims.add(new DimensionVO("44adde71-e67c-42fd-9ddf-f6f06008559a", "组织机构", "Organization"));
		budget_ctrl_dims.add(new DimensionVO("cbd09196-dcc5-45dd-9479-b61d30514af1", "费用科目", "ExpenseAccount"));
		budget_ctrl_dims.add(new DimensionVO("7cfad96c-8257-48eb-9b88-7336cee499c2", "项目", "Project"));
		budget_ctrl_dims.add(new DimensionVO("74e7d447-20be-4318-90a7-ec6073baff51", "岗位", "Post"));
		budget_ctrl_dims.add(new DimensionVO("fb8d49aa-a102-4865-bc78-76a0997085ea", "职级", "Rank"));
		
		
		
		members.add(new MemberVO("44adde71-e67c-42fd-9ddf-f6f06008559a", null, "组织机构", "Organization", true));
		
		members.add(new MemberVO("bf358908-2d43-4a14-a4e8-664d3b46e979", "44adde71-e67c-42fd-9ddf-f6f06008559a", "部门一", "Organization#Dept.1", true));
		members.add(new MemberVO("efa38882-46d4-4c34-906e-a694bccedec4", "bf358908-2d43-4a14-a4e8-664d3b46e979", "部门一.A", "Organization#Dept.1.a", false));
		members.add(new MemberVO("b2a0e99e-93ee-4bee-b5e7-8a42b1fd18f6", "bf358908-2d43-4a14-a4e8-664d3b46e979", "部门一.B", "Organization#Dept.1.b", false));
		members.add(new MemberVO("20634bd0-f126-4aef-a68a-2cfd3844a7db", "bf358908-2d43-4a14-a4e8-664d3b46e979", "部门一.C", "Organization#Dept.1.c", false));
		
		members.add(new MemberVO("b35798f1-53b5-41e7-9de6-ea733a243fb7", "44adde71-e67c-42fd-9ddf-f6f06008559a", "部门二", "Organization#Dept.2", true));
		members.add(new MemberVO("4ea0511f-4cff-47e0-8334-dd61a5d671ed", "b35798f1-53b5-41e7-9de6-ea733a243fb7", "部门二.D", "Organization#Dept.2.d", false));
		members.add(new MemberVO("cc34b8a4-1d4d-499e-8aad-5464e1f23301", "b35798f1-53b5-41e7-9de6-ea733a243fb7", "部门二.E", "Organization#Dept.2.e", false));
		
		members.add(new MemberVO("d1835acf-a1f3-4a8a-9a6f-45c0762d4d49", "44adde71-e67c-42fd-9ddf-f6f06008559a", "部门三", "Organization#Dept.3", true));
		members.add(new MemberVO("280e10bf-d571-4e55-b0d5-c2c0db5afa64", "d1835acf-a1f3-4a8a-9a6f-45c0762d4d49", "部门三.F", "Organization#Dept.3.f", false));
		members.add(new MemberVO("21142b1d-e48f-468e-aa7f-fc8855fe7762", "d1835acf-a1f3-4a8a-9a6f-45c0762d4d49", "部门三.G", "Organization#Dept.3.g", false));
	

		
		members.add(new MemberVO("cbd09196-dcc5-45dd-9479-b61d30514af1", null, "费用科目", "ExpenseAccount", true));
		
		members.add(new MemberVO("8d748e2a-7970-442c-9c43-29432c8425ba", "cbd09196-dcc5-45dd-9479-b61d30514af1", "科目一", "ExpenseAccount#Dept.1", true));
		members.add(new MemberVO("22ce1ec8-8ae2-45de-a8c5-d11803438959", "8d748e2a-7970-442c-9c43-29432c8425ba", "科目一.A", "ExpenseAccount#Dept.1.a", false));
		members.add(new MemberVO("a7edee38-7a64-49e5-b707-679b233e6fb2", "8d748e2a-7970-442c-9c43-29432c8425ba", "科目一.B", "ExpenseAccount#Dept.1.b", false));
		members.add(new MemberVO("af6263c7-1a9f-475c-9d6e-7587a88d99bc", "8d748e2a-7970-442c-9c43-29432c8425ba", "科目一.C", "ExpenseAccount#Dept.1.c", false));
		
		members.add(new MemberVO("b43781da-b85a-4d3c-a7a9-f861bd0f0726", "cbd09196-dcc5-45dd-9479-b61d30514af1", "科目二", "ExpenseAccount#Dept.2", true));
		members.add(new MemberVO("588c731d-fa32-4f0e-ab5f-05bb17a26e85", "b43781da-b85a-4d3c-a7a9-f861bd0f0726", "科目二.D", "ExpenseAccount#Dept.2.d", false));
		members.add(new MemberVO("d2580c06-c9dc-4d88-922b-016737b0bc8c", "b43781da-b85a-4d3c-a7a9-f861bd0f0726", "科目二.E", "ExpenseAccount#Dept.2.e", false));
		
		members.add(new MemberVO("4b70e734-f247-4e2b-9352-e5ccbaefeefe", "cbd09196-dcc5-45dd-9479-b61d30514af1", "科目三", "ExpenseAccount#Dept.3", true));
		members.add(new MemberVO("2c2f0438-490a-41ba-ab05-3533641dacaa", "4b70e734-f247-4e2b-9352-e5ccbaefeefe", "科目三.F", "ExpenseAccount#Dept.3.f", false));
		members.add(new MemberVO("05943794-78b5-426b-a748-109082fe8ba4", "4b70e734-f247-4e2b-9352-e5ccbaefeefe", "科目三.G", "ExpenseAccount#Dept.3.g", false));
	}

	@Override
	public List<DimensionVO> budgetCtrlDimensions() {
		return budget_ctrl_dims;
		// code ...
	}

	@Override
	public List<TreeViewNodeForLazyLoad> dimensionMembers(String dimensionCode, String parentId) {
		if(StringUtils.isBlank(dimensionCode)) {
			throw new IllegalArgumentException("The parameter 'dimensionCode' is null or empty.");
		}
		
		boolean root = StringUtils.isBlank(parentId);
		List<TreeViewNodeForLazyLoad> nodes = new ArrayList<TreeViewNodeForLazyLoad>();
		
		for (MemberVO member : members) {
			if(root && StringUtils.isBlank(member.getParentid()) && member.getMembercode().equals(dimensionCode) 
			|| parentId.equals(member.getParentid()) && member.getMembercode().startsWith(dimensionCode + "#")) {
				TreeViewNodeForLazyLoad node = new TreeViewNodeForLazyLoad();
				node.setText(member.getMembername());
				ReflectUtils.scanFields(MemberVO.class, new Visitor<Field, Boolean>() {
					public Boolean visit(Field field) {
						Object value = ReflectUtils.getValue(field, member);
						node.getAttrs().put(field.getName(), value == null ? "" : value) ;
						return false;
					}
				}, false, ReflectUtils.MODIFIER_STATIC$FINAL);
				
				node.setLazyLoad(member.isHaschildren());
				
				if(node.getState() == null) {
					node.setState(new TreeViewNodeState());	
				}
				
				nodes.add(node);
			}
		}
		return nodes;
		// code ...
	}
}
