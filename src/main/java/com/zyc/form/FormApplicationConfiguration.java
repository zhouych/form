package com.zyc.form;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zyc.baselibs.SpringContextHolder;
import com.zyc.baselibs.aopv.ParamVerificationAspect;
import com.zyc.baselibs.aopv.VerificationRulerContainer;
import com.zyc.baselibs.db.mysql.MysqlScriptComponent;

@Configuration
public class FormApplicationConfiguration {

	@Bean
	public SpringContextHolder springContextHolder(ApplicationContext applicationContext) {
		return new SpringContextHolder(applicationContext);
	}
	
	/**
	 * 实例化<code>ParamVerificationAspect</code>（参数验证切面），开启参数验证Spring AOP。
	 * @see 
	 * <code>ParamVerificationAspect</code>类是包含Component注解的，实例化的另一种方式：</br>
	 * 在SpringBoot启动类，通过<code>ComponentScan</code>注解配置<code>ParamVerificationAspect</code>的包名进行扫描。
	 * @return
	 */
	@Bean
	public ParamVerificationAspect paramVerificationAspect() {
		return new ParamVerificationAspect();
	}
	
	@Bean
	public VerificationRulerContainer verificationRulerContainer() {
		return new VerificationRulerContainer();
	}

	@Bean
	public MysqlScriptComponent MysqlScriptComponent() {
		MysqlScriptComponent mysqlScriptComponent = new MysqlScriptComponent();
		
		List<String> sqlscripts = mysqlScriptComponent.entity2tableSqlScripts("com.zyc.form.entities");
    	if(sqlscripts != null && !sqlscripts.isEmpty()) {
    		for (String sqlscript : sqlscripts) {
				System.out.println(sqlscript);
			}
    	}
    	
		return mysqlScriptComponent;
	}
}
