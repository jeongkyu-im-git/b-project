package com.test.bookproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.bookproject.service.impl.MainServiceImpl;

@Controller
public class MainController {

	@Autowired
	private MainServiceImpl mainService;
	
	@RequestMapping("/")
	public String main() throws Exception {
		
		mainService.init();
		
		return "main";
	}
	
}
