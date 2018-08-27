package com.zyc.form;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class FormApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(FormApplication.class).web(WebApplicationType.SERVLET).run(args);
	}
}
