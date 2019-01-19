package com.supbank.dao.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supbank.base.DBService;
import com.supbank.base.DataRow;

@Service
public class SearchService {

	private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
	
	@Autowired
	private DBService dbService;
	
	
	public DataRow getTestInfo(HttpServletRequest request) {
		DataRow result = new DataRow();
		DataRow<String,String> param = new DataRow<String,String>();
		param.put("userid", "000");
		try {
			dbService.Insert("td_user", param);
			System.out.println("**********************");
			result.put("flag", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
}
