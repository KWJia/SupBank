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
import com.supbank.util.RSAUtils;
import com.supbank.util.SHA256Util;

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
	
	@Autowired
	private WalletService walletService;
	
	/**
	 * 通过transactionid查询交易详细信息
	 * @param request
	 * @param params
	 * @return
	 */
	public DataRow getTransactionInfoById(HttpServletRequest request, DataRow params) {
		DataRow result = new DataRow();
		String sql = "select transactionid,input,output,sum,timestamp,blockid from td_transaction where flag=1 and status=2 and transactionid='"+params.getString("transactionId")+"'";
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
	public DataRow generateTransaction(HttpServletRequest request, DataRow params) {
		
		DataRow result = new DataRow();
		String id = GeneratorIDUtil.generatorId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		params.put("transactionid", id);
//		params.put("timestamp", sdf.format(date));
		params.put("timestamp", DateTimeUtil.getNowDateStr());
		params.put("status", 1);
		/*签名操作*/
		String input = params.getString("input");
		String output = params.getString("output");
		String sum = params.getString("sum");
		String tx_infor = id+input+output+sum;
		
		DataRow privKey_result = walletService.getPrivateKeyByAddress(input);
		if(privKey_result.getInt("flag")==1) {
			String privKey = privKey_result.getString("privateKey");
			String signature;
			try {
				signature = RSAUtils.sign(tx_infor.getBytes(), privKey);
//				System.out.println(signature);
				params.put("signature", signature);
				dbService.Insert("td_transaction", params);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				result.put("status", 1);
				result.put("errorMessage", "create tx error");
				e.printStackTrace();
			}
			result.put("status", 0);
			result.put("successMessage", "create tx success");
			
		}else {
			result.put("status", 1);
			result.put("errorMessage", "getPrivKeyByAddress error");
		}
		
		return result;
	}
	
	
	
	/**
	 * 查询近期交易（App）
	 * @param request
	 * @return
	 */
	public DataRow getRecentTransactions(HttpServletRequest request) {
		DataRow result = new DataRow();
		String username = request.getSession().getAttribute("username").toString();
		//查询user的address
		String sql1 = "SELECT a.address FROM td_wallet a,td_user b WHERE b.username='"+username+"' AND a.walletid=b.walletid AND b.flag=1";
		try {
			DataRow value = dbService.querySimpleRowBySql(sql1);
			String address = value.getString("address");
			//查询最近交易列表
			String sql = "SELECT transactionid,input,output,sum,blockid,timestamp FROM td_transaction WHERE flag=1 AND STATUS=2 AND (input='"+address+"' OR output='"+address+"') ORDER BY TIMESTAMP DESC";
			List<DataRow> transactionList = dbService.queryForList(sql);
			result.put("status", 0);
			result.put("successMessage", "query success");
			result.put("transactionList", transactionList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.put("status", 1);
			result.put("errorMessage", "query error");
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
}
