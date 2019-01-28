package com.supbank.dao.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supbank.base.DBService;
import com.supbank.base.DataRow;

/**
 * 有关交易的Service
 * @author kwj19
 *
 */
@Service
public class TransactionService {

	public static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
	
	@Autowired
	private DBService dbService;
	
	/**
	 * 通过transactionid查询交易详细信息
	 * @param request
	 * @param params
	 * @return
	 */
	public DataRow getTransactionInfoById(HttpServletRequest request, DataRow params) {
		DataRow result = new DataRow();
		String sql = "select transactionid,input,output,inputaddress,outputaddress,sum,timestamp,blockid from td_transaction where flag=1 and transactionid='"+params.getString("transactionId")+"'";
		List<DataRow> transactionList = dbService.queryForList(sql);
		if(transactionList.isEmpty()) {
			result.put("ack", "error");
			result.put("errorMessage", "0 result");
			result.put("timeStamp", System.currentTimeMillis());
		}else {
			result.put("ack", "success");
			result.put("errorMessage", "");
			result.put("timeStamp", System.currentTimeMillis());
			result.put("transactionList", transactionList);
		}
		return result;
		
	}
}
