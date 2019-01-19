package com.supbank.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.supbank.base.DataRow;
import com.supbank.dao.service.SearchService;
import com.supbank.util.JsonUtil;

@RestController
@RequestMapping("/kong")
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@CrossOrigin
	@ResponseBody
	@PostMapping("/test")
	public String getTestInfo(HttpServletRequest request) {
		DataRow result = null;
		System.out.println("+++++++++++++++++++++++++++++----");
		result = searchService.getTestInfo(request);
		System.out.println("---------------------------");
		return JsonUtil.resultJsonString(result);
		
	}
	
	
	@PostMapping("/sayPost")
	public String sayPost() {
		return "postSuccess";
	}
}
