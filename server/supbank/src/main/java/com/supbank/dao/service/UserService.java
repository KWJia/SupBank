package com.supbank.dao.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.supbank.base.DBService;
import com.supbank.base.DataRow;
import com.supbank.util.GeneratorIDUtil;
import com.supbank.util.MD5Util;

/**
 * user service
 * @author kwj19
 *
 */
@Service
public class UserService {

	@Autowired
	private DBService dbService;
	@Autowired
	private WalletService walletService;
	
	/**
	 * 注册
	 * @param request
	 * @param params
	 * @return
	 */
	public DataRow registerUser(HttpServletRequest request, DataRow params) {
		DataRow result = new DataRow<>();
	
		String code = params.getString("verificationCode");
		HttpSession session = request.getSession();
		JSONObject jsonobj = (JSONObject)session.getAttribute("verifyCode");

		if(jsonobj==null) {
			result.put("errorMessage", "verifyCode expire");
			result.put("status", 1);
			result.put("errorStatus", 0);
			return result;
		}
		
		if(!jsonobj.getString("code").equals(code)) {
			result.put("errorMessage", "verifyCode error");
			result.put("status", 1);
			result.put("errorStatus", 0);
			return result;
		}
		
		if((System.currentTimeMillis() - jsonobj.getLong("timestamp")) >= 5*60*1000) {
			result.put("errorMessage", "verifyCode expire");
			result.put("status", 1);
			result.put("errorStatus", 0);
			return result;
		}
		
		
		
		DataRow data = new DataRow();
		String userid = GeneratorIDUtil.generatorId();
		
		String username = params.getString("username");
		String password = MD5Util.makeMD5(params.getString("password"));
		String email = params.getString("email");
		int accouttype = params.getInt("accoutType");
		data.put("userid", userid);
		
		data.put("username", username);
		data.put("password", password);
		data.put("email", email);
		data.put("accouttype", accouttype);
		
		String sql1 = "select * from td_user where username='"+username+"' and flag=1";
		String sql2 = "select * from td_user where email='"+email+"' and flag=1";
		List<DataRow> usernameList = dbService.queryForList(sql1);
		List<DataRow> emailList = dbService.queryForList(sql2);
		if(usernameList.isEmpty()&&emailList.isEmpty()) {
			//创建钱包
			DataRow wallet_result = walletService.createWallet();
			if(wallet_result.getInt("flag")==1) {
				result.put("errorMessage", "create wallet failed");
				result.put("errorStatus",4);
				result.put("status", 1);
				return result;
			}
			String walletid = wallet_result.getString("walletid");
			data.put("walletid", walletid);
			try {
				dbService.Insert("td_user", data);
			} catch (Exception e) {
				result.put("errorMessage", "create user failed");
				result.put("status", 1);
				result.put("errorStatus",4);
				e.printStackTrace();
				return result;
			}
			result.put("status", 0);
			result.put("successMessage", "create user success");
			result.put("walletInfor", wallet_result);
			return result;
		}else {
			if(!usernameList.isEmpty()) {
				result.put("errorMessage", "username has existed");
				result.put("status", 1);
				result.put("errorStatus",1);
				return result;
			}
			if(!emailList.isEmpty()) {
				result.put("errorMessage", "email has been registed");
				result.put("status", 1);
				result.put("errorStatus",2);
				return result;
			}
			
		}
		return result;
		
		
		
	}
	
	
	
	/**
	 * 登陆
	 * @param request
	 * @param params
	 * @return
	 */
	public DataRow login(HttpServletRequest request, DataRow params) {
		DataRow result = new DataRow();
		String username = params.getString("username");
		String password = params.getString("password");
		String sql1 = "select username from td_user where username='"+username+"' and flag=1";
		String sql2 = "select * from td_user where username='"+username+"' and password='"+MD5Util.makeMD5(password)+"' and flag=1";
		List<DataRow> usernameList = dbService.queryForList(sql1);
		if(usernameList.isEmpty()) {
			result.put("status", 1);
			result.put("errorStatus", 0);
			result.put("errorMessage", "username error");
			return result;
		}
		List<DataRow> user = dbService.queryForList(sql2);
		if(user.isEmpty()) {
			result.put("status", 1);
			result.put("errorStatus", 1);
			result.put("errorMessage", "password error");
			return result;
		}else {
			result.put("status", 0);
			result.put("successMessage", "login success");
			return result;
		}
		
		
	}
	
	
	
	
	
	
	
	
	
}
