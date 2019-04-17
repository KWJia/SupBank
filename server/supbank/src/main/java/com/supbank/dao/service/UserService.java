package com.supbank.dao.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.supbank.base.DBService;
import com.supbank.base.DataRow;

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
