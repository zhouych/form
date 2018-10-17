package com.zyc.form.client.ribbon;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zyc.form.client.MdataClient;
import com.zyc.form.client.vo.DimensionVO;

/**
 * 基于ribbon的支持负载均衡的负责与主数据微服务（Mdata）进行交互的客户端
 * @author zhouyancheng
 *
 */
@Service
public class MdataClientRibbonImpl implements MdataClient {
	
	static final List<DimensionVO> budget_ctrl_dims = new ArrayList<DimensionVO>();
	
	static {
		budget_ctrl_dims.add(new DimensionVO("44adde71-e67c-42fd-9ddf-f6f06008559a", "组织机构", "Organization"));
		budget_ctrl_dims.add(new DimensionVO("cbd09196-dcc5-45dd-9479-b61d30514af1", "费用科目", "ExpenseAccount"));
		budget_ctrl_dims.add(new DimensionVO("7cfad96c-8257-48eb-9b88-7336cee499c2", "项目", "Project"));
		budget_ctrl_dims.add(new DimensionVO("74e7d447-20be-4318-90a7-ec6073baff51", "岗位", "Post"));
		budget_ctrl_dims.add(new DimensionVO("fb8d49aa-a102-4865-bc78-76a0997085ea", "职级", "Rank"));
	}

	@Override
	public List<DimensionVO> budgetCtrlDimensions() {
		return budget_ctrl_dims;
		// code ...
	}

}
