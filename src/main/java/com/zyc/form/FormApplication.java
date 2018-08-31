package com.zyc.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class FormApplication {
	
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return restTemplateBuilder.build();
	}
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(FormApplication.class).web(WebApplicationType.SERVLET).run(args);
	}
}
