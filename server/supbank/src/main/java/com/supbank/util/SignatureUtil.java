package com.supbank.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

/**
 * 签名工具
 * @author kwj19
 *
 */
public class SignatureUtil {

	private static byte[] executeSignature(String data,PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException{
        
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        byte[] result = signature.sign();
        
        return result;
    }
}
