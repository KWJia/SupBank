package com.supbank.dao.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supbank.base.DBService;
import com.supbank.base.DataRow;
import com.supbank.util.GeneratorIDUtil;
import com.supbank.util.RSAUtils;

@Service
public class WalletService {

	@Autowired
	private DBService dbService;
	
	/**
	 * 新建钱包
	 * @return
	 */
	public DataRow createWallet() {
		DataRow params = new DataRow();
		String walletid = GeneratorIDUtil.generatorId();
		Map<String, Object> keyPairs;
		String publicKey = null;
		String privateKey = null;
		String address = null;
		double balance = 0;
		try {
			keyPairs = RSAUtils.genKeyPair();
			publicKey = RSAUtils.getPublicKey(keyPairs);
			privateKey = RSAUtils.getPrivateKey(keyPairs);
			address = (String) keyPairs.get("address");
			balance = 0;
			params.put("walletid", walletid);
			params.put("publicKey", publicKey);
			params.put("privateKey", privateKey);
			params.put("address", address);
			params.put("balance", balance);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataRow result = new DataRow();
		try {
			dbService.Insert("td_wallet", params);
		} catch (Exception e) {
			result.put("flag", 1);//添加失败
			e.printStackTrace();
			return result;
		}
		result.put("flag", 0);
		result.put("walletid", walletid);
		result.put("publicKey", publicKey);
		result.put("privateKey", privateKey);
		result.put("address", address);
		
		return result;
		
	}
}
