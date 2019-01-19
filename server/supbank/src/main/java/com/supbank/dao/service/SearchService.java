package com.supbank.dao.service;

import java.util.List;

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
	
	
	public DataRow getTestInfo(HttpServletRequest request, DataRow<String,String> param) {
		DataRow result = new DataRow();
		String keyword = param.getString("keyword");
		String sql1 = "select transactionid,input,output,sum from td_transaction where flag=1 and transactionid='"+keyword+"'";
		String sql2 = "select height,hash,transactionnumber,miner from td_block where flag=1 and height='"+keyword+"'";
		String sql3 = "select height,hash,transactionnumber,miner from td_block where flag=1 and hash='"+keyword+"'";
		List<DataRow> transactionList = dbService.queryForList(sql1);
		List<DataRow> blockList1 = dbService.queryForList(sql2);
		List<DataRow> blockList2 = dbService.queryForList(sql3);
		blockList1.addAll(blockList2);
		if(transactionList.isEmpty()&&blockList1.isEmpty()) {
			result.put("ack", "error");
			result.put("errorMessage", "0 result");
			result.put("timeStamp", System.currentTimeMillis());
		}else {
			result.put("ack", "success");
			result.put("errorMessage", "");
			result.put("timeStamp", System.currentTimeMillis());
			result.put("transactionList", transactionList);
			result.put("blockList", blockList1);
		}
		return result;
	}
	
	
	
}
