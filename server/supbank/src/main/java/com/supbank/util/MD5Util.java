package com.supbank.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

public class MD5Util {
	public final static String SECRET_KEY = "zybase";
	public static String makeMD5(String password) {   
		MessageDigest md;   
		try {
			byte[] btInput = password.getBytes("utf-8");
		    // 生成一个MD5加密计算摘要   
			md = MessageDigest.getInstance("MD5");   
		    // 计算md5函数   
		    md.update(btInput);   
		    // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符   
		    // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值   
		    String pwd = new BigInteger(1, md.digest()).toString(16);   
		    //System.err.println(pwd);   
		    return pwd;   
		} catch (Exception e) {   
			e.printStackTrace();   
		}   
		return password;   
	}
	
	public static String makePwd(String staffid,String pwd)
	{
		return MD5Util.makeMD5(staffid.toUpperCase()+pwd+MD5Util.SECRET_KEY);
	}
	
	/*public static void main(String[] args)
	{
		System.out.println(MD5Util.makeMD5("YJ0021"+"123"));
		ArrayList<String> staff = new ArrayList();
		staff.add("BG0001");
		for(String st : staff)
		{
			System.out.println("update td_userinfo set companyid='111',password='"+MD5Util.makeMD5(st.toUpperCase()+"123")+"' where staffid='"+st+"';");
		}
		
	}*/
}
