package com.supbank.util;



import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.TestNet3Params;

import com.supbank.base.DataRow;

import sun.misc.BASE64Decoder;

/**
 * 生成密钥
 * @author kwj19
 *
 */
public class GenerateKeyUtil {


//	public static DataRow getKey() {
//		
//		
//		NetworkParameters netParams;
//		//testNet
//		netParams = TestNet3Params.get();
//		ECKey ecKey = new ECKey();
//		String privKey = ecKey.getPrivateKeyAsHex();
//		String pubKey = ecKey.getPublicKeyAsHex();
//		DataRow key = new DataRow();
//		try {
//			String address = RipeMD160Util.encodeRipeMD160Hex(SHA256Util.SHA256(pubKey).getBytes());
//			
//			key.put("privKey",privKey);
//			key.put("pubKey", pubKey);
//			key.put("address", address);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	
//	
//		return key;
//	}
//	
	
	/**
	 * 以字符串生成密钥对
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static Object[] getKeys(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        Object[] keyPairArr = new Object[2];
        keyPairArr[0] = publicKey;
        keyPairArr[1] = privateKey;
        return keyPairArr;
  }
	
	
	
	
	
}
