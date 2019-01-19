package com.supbank.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.supbank.base.DataRow;

public class MapUtil {
	/**
	 * 判断DataRow是否为空或null
	 * 
	 * @return true or false
	 */
	public static boolean isEmpty(DataRow row) {
		if (row == null)
			return true;
		else if (row.isEmpty())
			return true;
		else
			return false;
	}
}
