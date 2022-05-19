package com.example.securitylogin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.securitylogin.infra.line.api.v2.LineAPIService;

@Controller
public class LineController {
	@Autowired
	LineAPIService lineAPIService;
	
	@RequestMapping(value = "/push-message", method = RequestMethod.POST)
	public String pushMessage(@RequestParam(name = "message") String message,
			@RequestParam(name = "username") String username, 
			Model model) {
		lineAPIService.pushMessage(username, message);
		return "admin/home";
	}
}
