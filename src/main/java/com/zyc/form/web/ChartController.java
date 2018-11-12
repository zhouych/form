package com.zyc.form.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ChartController {
	
    @RequestMapping(value = "/charts/test", method = RequestMethod.GET)
	public String test(Model model) {
		return "/charts/test";
	}
}
