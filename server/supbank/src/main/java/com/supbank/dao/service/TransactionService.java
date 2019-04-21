package com.supbank.dao.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supbank.base.DBService;
import com.supbank.base.DataRow;
import com.supbank.util.DateTimeUtil;
import com.supbank.util.GeneratorIDUtil;

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
			result.put("status",1);
			result.put("errorMessage", "0 result");
		}else {
			result.put("status", 0);
			result.put("transactionList", transactionList);
		}
		return result;
		
	}
	
	/**
	 * 产生交易（是否返回值）
	 * @param request
	 * @param params
	 * @return
	 */
	public void generateTransaction(HttpServletRequest request, DataRow params) {
		
		String id = GeneratorIDUtil.generatorId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		params.put("transactionid", id);
//		params.put("timestamp", sdf.format(date));
		params.put("timestamp", DateTimeUtil.getNowDateStr());
		params.put("status", 1);
		
		params.put("flag", 1);
		
		try {
			dbService.Insert("td_transaction", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
