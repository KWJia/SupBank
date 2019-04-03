package com.supbank.util;

import java.security.PublicKey;
import java.security.Signature;

/**
 * 验证签名工具
 * 
 * @author kwj19
 *
 */
public class VerifySignatureUtil {

	public static boolean verify(String data, byte[] sign, PublicKey pubk) throws Exception {
		
		Signature sig = Signature.getInstance("MD5withRSA");
		sig.initVerify(pubk);
		sig.update(data.getBytes());
		return sig.verify(sign);
	}

}
