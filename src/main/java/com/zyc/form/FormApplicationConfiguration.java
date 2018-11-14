package com.zyc.form;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zyc.baselibs.SpringContextHolder;
import com.zyc.baselibs.annotation.FieldRuleUtils;
import com.zyc.baselibs.annotation.MainfieldUtils;
import com.zyc.baselibs.annotation.SubfieldUtils;
import com.zyc.baselibs.aopv.ParamVerificationAspect;
import com.zyc.baselibs.aopv.VerificationRulerContainer;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.data.DataStatus;
import com.zyc.baselibs.db.mysql.MysqlScriptComponent;
import com.zyc.baselibs.service.GeneralDataService;
import com.zyc.baselibs.service.GeneralDataServiceImpl;
import com.zyc.baselibs.web.GeneralDataRestController;
import com.zyc.form.data.FieldDataType;
import com.zyc.form.data.FieldDisplayType;
import com.zyc.form.data.FormArea;
import com.zyc.form.entities.Edocument;
import com.zyc.form.entities.EdocumentDetail;
import com.zyc.form.entities.EdocumentPayment;
import com.zyc.form.entities.EdocumentReversal;
import com.zyc.form.entities.MetaField;

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
	public MysqlScriptComponent mysqlScriptComponent() {
		MysqlScriptComponent mysqlScriptComponent = new MysqlScriptComponent();
		
		System.out.println("\n\n");
		
		List<String> sqlscripts = mysqlScriptComponent.entity2tableSqlScripts("com.zyc.form.entities");
    	if(sqlscripts != null && !sqlscripts.isEmpty()) {
    		for (String sqlscript : sqlscripts) {
				System.out.println(sqlscript);
			}
    	}

		System.out.println("\n\n");
		
    	String formatInsertSql = mysqlScriptComponent.formatInsertSqlScripts(MetaField.class);
    	Class<?>[] clazzs = new Class<?>[] { Edocument.class, EdocumentDetail.class, EdocumentPayment.class, EdocumentReversal.class };
    	String ingores = "datastatus;createdat;updatedat;version;";
    	for (Class<?> clazz : clazzs) {
        	ReflectUtils.scanFields(clazz, new Visitor<Field, Boolean>() {
    			@Override
    			public Boolean visit(Field field) {
    				if(SubfieldUtils.isSubfield(field) || ingores.contains(field.getName() + ";")) {
    					return false;
    				}
    				
    				String suffix = clazz.getSimpleName().replace("Edocument", "");
    				FormArea area = StringUtils.isBlank(suffix) ? FormArea.MAIN : Enum.valueOf(FormArea.class, suffix.toUpperCase());
    				FieldDataType dataType = Enum.valueOf(FieldDataType.class, field.getType().getSimpleName().toUpperCase());
    				FieldDisplayType displayType = MainfieldUtils.isMainfield(field) && !field.getName().startsWith("item") ? FieldDisplayType.TREE : FieldDisplayType.TEXTBOX;
    				boolean editable = !FieldRuleUtils.externalUneditable(field);
    				
    				String sql = formatInsertSql;
    				sql = sql.replace("values(", "\n\tvalues(");
    				sql = sql.replace("@id", "'" + UUID.randomUUID().toString() + "'");
    				sql = sql.replace("@formarea", "'" + area.getValue() + "'");
    				sql = sql.replace("@sysfield", field.getName().startsWith("item") ? "0" : "1");
    				sql = sql.replace("@fieldvalue", "'" + field.getName() + "'");
    				sql = sql.replace("@fieldname", "'" + field.getName() + "'");
    				sql = sql.replace("@datatype", "'" + dataType.getValue() + "'");
    				sql = sql.replace("@displaytype", "'" + displayType.getValue() + "'");
    				sql = sql.replace("@expressiontext", "null");
    				sql = sql.replace("@expressiondefault", "null");
    				sql = sql.replace("@expression", "null");
    				sql = sql.replace("@editable", editable ? "1" : "0");
    				sql = sql.replace("@description", "null");
    				sql = sql.replace("@datastatus", "'" + DataStatus.ENABLED.getValue() + "'");
    				sql = sql.replace("@createdat", "current_timestamp()");
    				sql = sql.replace("@updatedat", "current_timestamp()");
    				sql = sql.replace("@version", "0");
    				sql += ";";
    				
    				System.out.println(sql);
    				return false;
    			}
    		}, false, ReflectUtils.MODIFIER_STATIC$FINAL);
		}
    	
		System.out.println("\n\n");
    	
		return mysqlScriptComponent;
	}

	@Bean
	public GeneralDataService generalDataService() {
		return new GeneralDataServiceImpl();
	}
	
	@Bean
	public GeneralDataRestController generalDataRestController(GeneralDataService generalDataService) {
		return new GeneralDataRestController(generalDataService);
	}
}
