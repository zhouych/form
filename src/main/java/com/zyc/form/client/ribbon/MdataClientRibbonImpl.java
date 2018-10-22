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
		
		members.add(new MemberVO("bf358908-2d43-4a14-a4e8-664d3b46e979", "44adde71-e67c-42fd-9ddf-f6f06008559a", "部门一", "Dept.1", true));
		members.add(new MemberVO("efa38882-46d4-4c34-906e-a694bccedec4", "bf358908-2d43-4a14-a4e8-664d3b46e979", "部门一.A", "Dept.1.a", false));
		members.add(new MemberVO("b2a0e99e-93ee-4bee-b5e7-8a42b1fd18f6", "bf358908-2d43-4a14-a4e8-664d3b46e979", "部门一.B", "Dept.1.b", false));
		members.add(new MemberVO("20634bd0-f126-4aef-a68a-2cfd3844a7db", "bf358908-2d43-4a14-a4e8-664d3b46e979", "部门一.C", "Dept.1.c", false));
		
		members.add(new MemberVO("b35798f1-53b5-41e7-9de6-ea733a243fb7", "44adde71-e67c-42fd-9ddf-f6f06008559a", "部门二.", "Dept.2", true));
		members.add(new MemberVO("4ea0511f-4cff-47e0-8334-dd61a5d671ed", "b35798f1-53b5-41e7-9de6-ea733a243fb7", "部门二.D", "Dept.2.d", false));
		members.add(new MemberVO("cc34b8a4-1d4d-499e-8aad-5464e1f23301", "b35798f1-53b5-41e7-9de6-ea733a243fb7", "部门二.E", "Dept.2.e", false));
		
		members.add(new MemberVO("d1835acf-a1f3-4a8a-9a6f-45c0762d4d49", "44adde71-e67c-42fd-9ddf-f6f06008559a", "部门三", "Dept.3", true));
		members.add(new MemberVO("280e10bf-d571-4e55-b0d5-c2c0db5afa64", "d1835acf-a1f3-4a8a-9a6f-45c0762d4d49", "部门三.F", "Dept.3.f", false));
		members.add(new MemberVO("21142b1d-e48f-468e-aa7f-fc8855fe7762", "d1835acf-a1f3-4a8a-9a6f-45c0762d4d49", "部门三.G", "Dept.3.g", false));
	}

	@Override
	public List<DimensionVO> budgetCtrlDimensions() {
		return budget_ctrl_dims;
		// code ...
	}

	@Override
	public List<TreeViewNodeForLazyLoad> dimensionMembers(String parentId) {
		boolean root = StringUtils.isBlank(parentId);
		List<TreeViewNodeForLazyLoad> nodes = new ArrayList<TreeViewNodeForLazyLoad>();
		
		for (MemberVO member : members) {
			if(root && StringUtils.isBlank(member.getParentid()) || parentId.equals(member.getParentid())) {
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
