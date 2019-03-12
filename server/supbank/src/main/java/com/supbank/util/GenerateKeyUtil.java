package com.supbank.util;



import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.TestNet3Params;

import com.supbank.base.DataRow;

/**
 * 生成密钥
 * @author kwj19
 *
 */
public class GenerateKeyUtil {


	public static DataRow getKey() {
		
		
		NetworkParameters netParams;
		//testNet
		netParams = TestNet3Params.get();
		ECKey ecKey = new ECKey();
		String privKey = ecKey.getPrivateKeyAsHex();
		String pubKey = ecKey.getPublicKeyAsHex();
		DataRow key = new DataRow();
		try {
			String address = RipeMD160Util.encodeRipeMD160Hex(SHA256Util.SHA256(pubKey).getBytes());
			
			key.put("privKey",privKey);
			key.put("pubKey", pubKey);
			key.put("address", address);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	
		return key;
	}
}
