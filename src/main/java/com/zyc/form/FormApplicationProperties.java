package com.zyc.form;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.zyc.baselibs.AbstractSpringBoot2ApplicationProperties;

@PropertySource({ "classpath:application.yml" })
@Service
public class FormApplicationProperties extends AbstractSpringBoot2ApplicationProperties {

}
