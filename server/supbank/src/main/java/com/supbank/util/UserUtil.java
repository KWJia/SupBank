package com.supbank.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.supbank.base.DataRow;


public class UserUtil
{
	private static final Logger logger = LoggerFactory.getLogger(UserUtil.class);
	private static final Feature DataRow = null;

	/**
	 * 获取OnlineStaff
	 * 
	 * @author nge
	 * @date 2016年8月29日
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static DataRow getOnlineStaff(HttpServletRequest request)
	{
		Object obj = request.getSession().getAttribute("OnlineStaff");
		if (obj != null)
		{
			DataRow map =JSON.parseObject(obj.toString(), DataRow.class);	
			return map;
		}
		
		logger.error("session中获得用户信息失败,当前的sessionid为:{}",request.getSession().getId());
		return null;
	}
	
	public static String getOnlineStaffDeptId(HttpServletRequest request)
	{
		DataRow onlineStaff = getOnlineStaff(request);
		if (!MapUtil.isEmpty(onlineStaff)) {
			return onlineStaff.getString("deptid");
		}
		return null;
	}

	public static boolean hasFunctionRight(HttpServletRequest request, String functionright)
	{
		logger.debug("hasFunctionRight  ------------------------->" + functionright);
		DataRow onlineStaff = getOnlineStaff(request);
		if (!MapUtil.isEmpty(onlineStaff) && onlineStaff.get("functionright") != null)
		{
			Object obj = onlineStaff.get("functionright");
			DataRow  functions = JSON.parseObject(obj.toString(), DataRow.class);
			System.out.println(obj.getClass().getTypeName());
			//DataRow functions = (DataRow) onlineStaff.get("functionright");
			if (functions.containsKey(functionright))
			{
				return true;
			}
		}
		else
		{
			return false;
		}
		return false;
	}

	public static DataRow getFunctionRight(HttpServletRequest request)
	{
		DataRow onlineStaff = getOnlineStaff(request);
		if (!MapUtil.isEmpty(onlineStaff))
		{
			Object obj = onlineStaff.get("functionright");
			DataRow  functions = JSON.parseObject(obj.toString(), DataRow.class);
			return functions;
		}
		return null;
	}
	
	/**
	 * 判断用户是否有此岗位名称
	 * @param request
	 * @param roleid
	 * @return 返回是否
	 */
	public static boolean hasRoleName(HttpServletRequest request,String rolename)
	{
		logger.debug("hasRoleName  ------------------------->"+rolename);
		DataRow OnlineStaff = UserUtil.getOnlineStaff(request);
		if(OnlineStaff!=null)
		{
//			DataRow OnlineStaff = (DataRow)request.getSession().getAttribute("OnlineStaff");
			if(OnlineStaff.containsKey("role"))
			{
				DataRow userole =(DataRow)OnlineStaff.get("role");
				if(userole.containsValue(rolename))
				{
					return true;
				}
			}
		}else{
			return false;
		}
		return false;
	}

	public static DataRow getOnlineCompany(HttpServletRequest request)
	{
		DataRow onlineStaff = getOnlineStaff(request);
		if (!MapUtil.isEmpty(onlineStaff))
		{
			Object obj = onlineStaff.get("company");
			DataRow companyInfo = JSON.parseObject(obj.toString(), DataRow.class);
			companyInfo.transTo("name", "companyname");
			return companyInfo;
		}
		return null;
	}
	
	public static List<DataRow> getStaffMenuList(HttpServletRequest request)
	{
		Object obj = request.getSession().getAttribute("StaffMenuList");
		if (obj != null)
		{
			List<DataRow> StaffMenuList = JSON.parseArray(obj.toString(), DataRow.class);
			return StaffMenuList;
		}
		return null;
	}
}
