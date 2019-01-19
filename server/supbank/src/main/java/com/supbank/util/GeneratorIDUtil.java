package com.supbank.util;

import java.text.NumberFormat;
import java.util.Date;

public class GeneratorIDUtil {
public static int idnum = 1;
	/*
	 * 生成常用ID编码
	 */
	public synchronized static String generatorId()
	{
		if(idnum>=999)
		{
			idnum=1;
		}
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyMMddHHmmss");
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMaximumIntegerDigits(4);
		nf.setMinimumIntegerDigits(4);
        String datestr = format.format(new Date());
        return (datestr+nf.format(idnum++));
	}
	/*
	 * 生成委托ID编码
	 * 参数 默认 W
	 */
	public synchronized static String getOrderId(String head)
	{
		if(head==null)
		{
			head="W";
		}
		if(idnum>=999)
		{
			idnum=1;
		}
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyMMddHHmmss");
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMaximumIntegerDigits(4);
		nf.setMinimumIntegerDigits(4);
        String datestr = format.format(new Date());
        return (head+datestr+nf.format(idnum++));
	}
}
