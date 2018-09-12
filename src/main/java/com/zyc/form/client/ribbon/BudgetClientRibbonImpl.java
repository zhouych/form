package com.zyc.form.client.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zyc.form.client.BudgetClient;

/**
 * 基于ribbon的支持负载均衡的负责与预算微服务（Budget）进行交互的客户端
 * @author zhouyancheng
 *
 */
@Service
public class BudgetClientRibbonImpl implements BudgetClient {

	@Value("${api.budget.url}")
	private String apiBudgetUrl;

	@Autowired
	protected RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "budgetTypesFallback")
	public String budgetTypes() {
		return this.restTemplate.getForEntity(this.apiBudgetUrl + "/budgetTypes", String.class).getBody();
	}
	
	public String budgetTypesFallback() {
		return "Error: " + this.apiBudgetUrl;
	}
}
