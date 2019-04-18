package com.supbank.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.supbank.base.DataRow;
import com.supbank.dao.service.UserService;
import com.supbank.util.EmailUtil;
import com.supbank.util.JsonUtil;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@CrossOrigin
	@ResponseBody
	@PostMapping("/sendCode")
	public String sendCode(HttpServletRequest request, @RequestBody DataRow params) {
		DataRow result = new DataRow();
		JSONObject jsonobj = null;
		String code = EmailUtil.generateCode();
		String address = params.getString("email");
		boolean isSend = EmailUtil.sendEmail(address, code);
		if(isSend) {
			result.put("ack", "success");
			result.put("errorMessage", "send success");
		}else {
			result.put("ack", "error");
			result.put("errorMessage", "send failed");
			return JsonUtil.resultJsonString(result);
		}
		HttpSession session = request.getSession();
		jsonobj.put("code", code);
		jsonobj.put("timestamp", System.currentTimeMillis());
		session.setAttribute("verifyCode", jsonobj);
		
		
		return JsonUtil.resultJsonString(result);
	}
}
