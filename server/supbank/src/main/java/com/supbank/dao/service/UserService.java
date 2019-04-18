package com.supbank.dao.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.supbank.base.DBService;
import com.supbank.base.DataRow;
import com.supbank.util.EmailUtil;

/**
 * user service
 * @author kwj19
 *
 */
public class UserService {

	@Autowired
	private DBService dbService;
	
	public DataRow registerUser(HttpServletRequest request, DataRow params) {
		DataRow result = new DataRow<>();
		
		
		return result;
	}
	
	
	
	
}
