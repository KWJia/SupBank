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
@RequestMapping("/")
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@CrossOrigin
	@ResponseBody
	@PostMapping("/search")
	public String getTestInfo(HttpServletRequest request,@RequestBody DataRow<String,String> params) {
		DataRow result = null;
		result = searchService.getTestInfo(request,params);
		return JsonUtil.resultJsonString(result);
		
	}
	
	
	@PostMapping("/sayPost")
	public String sayPost() {
		return "postSuccess";
	}
}
