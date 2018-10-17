package com.zyc.form.client;

import java.util.List;

import com.zyc.form.client.vo.DimensionVO;

/**
 * 负责与主数据微服务（Mdata）进行交互的客户端接口
 * @author zhouyancheng
 *
 */
public interface MdataClient {
	
	List<DimensionVO> budgetCtrlDimensions();
}
