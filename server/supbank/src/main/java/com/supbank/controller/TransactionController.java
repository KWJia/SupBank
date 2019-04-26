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
import com.supbank.dao.service.TransactionService;
import com.supbank.util.JsonUtil;

/**
 * 有关交易的Controller
 * @author kwj19
 *
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	/**
	 * 通过transactionid查询交易详细信息
	 * @param request
	 * @param params
	 * @return
	 */
	@CrossOrigin
	@ResponseBody
	@PostMapping("/getTransactionById")
	public String getTransactionInfo(HttpServletRequest request, @RequestBody DataRow<String,String> params) {
		DataRow result = null;
		result = transactionService.getTransactionInfoById(request, params);
		return JsonUtil.resultJsonString(result);
	}
	
	
	/**
	 * 产生交易
	 * @param request
	 * @param params
	 * @return
	 */
	@CrossOrigin
	@ResponseBody
	@PostMapping("/transfer")
	public String createTransaction(HttpServletRequest request, @RequestBody DataRow<String,String> params) {
		DataRow result = transactionService.generateTransaction(request, params);
		return JsonUtil.resultJsonString(result);
	}
	
	
	
	
	/**
	 * 查询用户自己近期交易
	 * @param request
	 * @return
	 */
	@CrossOrigin
	@ResponseBody
	@PostMapping("/recentTx")
	public String queryRecentTransaction(HttpServletRequest request) {
		DataRow result = transactionService.getRecentTransactions(request);
		return JsonUtil.resultJsonString(result);
	}
	
	
	
}
