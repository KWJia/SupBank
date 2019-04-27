package com.supbank.dao.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		String sql = "select transactionid,input,output,sum,timestamp,blockid,status from td_transaction where flag=1 and transactionid='"+params.getString("transactionId")+"'";
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
		double sum = params.getDouble("sum");
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
	 * 验证交易并更新余额
	 * @param transactionid
	 * @param blockid
	 * @return
	 */
	@Transactional
	public DataRow txVerifyAndEditBalance(String transactionid,String blockid) {
		DataRow result = new DataRow();
		String input = "";
		String output = "";
		double sum = 0;
		//查询交易信息
		String txInforSQL = "select * from td_transaction where flag=1 and transactionid='"+transactionid+"'";
		DataRow tx = null;
		try {
			tx = dbService.querySimpleRowBySql(txInforSQL);
			if(tx.isEmpty()) {
				result.put("status", 1);
				result.put("errorMessage", "transaction is not existed");
				return result;
			}
		} catch (Exception e1) {
			result.put("status", 1);
			result.put("errorMessage", "query txInfor db error");
			e1.printStackTrace();
			return result;
		}
		input = tx.getString("input");
		output = tx.getString("output");
		sum = tx.getDouble("sum");
		String tx_infor = transactionid+input+output+sum;
		String signature = tx.getString("signature");
		//查询input用户余额
		double input_balance = 0;
		String publicKey="";
		String queryBalanceSQL = "select balance,publicKey from td_wallet where flag=1 and address='"+input+"'";
		try {
			DataRow wallet_infor = dbService.querySimpleRowBySql(queryBalanceSQL);
			input_balance = wallet_infor.getDouble("balance");
			publicKey = wallet_infor.getString("publicKey");
		} catch (Exception e) {
			result.put("status", 1);
			result.put("errorMessage", "query balance db error");
			e.printStackTrace();
			return result;
		}
		boolean verifyResult = true;
		if(input_balance>=sum) {
			//钱够用
			try {
				verifyResult = RSAUtils.verify(tx_infor.getBytes(), publicKey, signature);
			
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(verifyResult) {
				//验证通过
				String editInputSQL = "update td_wallet set balance="+(input_balance-sum)+" where flag=1 and address='"+input+"'";
				String editOutputSQL = "update td_wallet set balance=balance+"+sum+" where flag=1 and address='"+output+"'";
				String editTxSQL = "update td_transaction set status=2,blockid='"+blockid+"' where flag=1 and transactionid='"+transactionid+"'";
				try {
					dbService.UpdateBySql(editInputSQL);
					dbService.UpdateBySql(editOutputSQL);
					dbService.UpdateBySql(editTxSQL);
					result.put("status", 0);
					result.put("successMessage", "update balance and verify success");
				} catch (Exception e) {
					result.put("status", 1);
					result.put("errorMessage", "update balance db error");
					e.printStackTrace();
					return result;
				}
			}else {
				result.put("status", 1);
				result.put("errorMessage", "verify failed,data maybe changed");
			}
			
			
		}else {
			result.put("status", 1);
			result.put("errorMessage", "balance not enough");
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
