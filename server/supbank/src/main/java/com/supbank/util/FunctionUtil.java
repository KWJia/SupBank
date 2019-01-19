package com.supbank.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supbank.base.DataRow;

public class FunctionUtil
{
	private static final Logger logger = LoggerFactory.getLogger(FunctionUtil.class);

	public static boolean hasFunctionRight(HttpServletRequest request, String functionright)
	{
		logger.debug("hasFunctionRight  ------------------------->" + functionright);
		if (request.getSession().getAttribute("OnlineStaff") != null)
		{
			DataRow OnlineStaff = (DataRow) request.getSession().getAttribute("OnlineStaff");
			if (OnlineStaff.containsKey("functionright"))
			{
				DataRow functions = (DataRow) OnlineStaff.get("functionright");
				if (functions.containsKey(functionright))
				{
					return true;
				}
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
		if (request.getSession().getAttribute("OnlineStaff") != null)
		{
			DataRow OnlineStaff = (DataRow) request.getSession().getAttribute("OnlineStaff");
			if (OnlineStaff.containsKey("functionright"))
			{
				DataRow functions = (DataRow) OnlineStaff.get("functionright");
				return functions;
			}
		}
		return null;
	}

	/**
	 * 浮点数--加法
	 * 
	 * @author nge
	 * @date 2015年8月20日
	 * @param num1
	 * @param num2
	 * @return
	 * @throws Exception
	 */
	public static double MathPlus(String num1, String num2) throws Exception
	{
		double baseNum;
		int baseNum1, baseNum2;
		try
		{
			baseNum1 = num1.length() - num1.indexOf(".") - 1;
		}
		catch (Exception e)
		{
			baseNum1 = 0;
		}

		try
		{
			baseNum2 = num2.length() - num2.indexOf(".") - 1;
		}
		catch (Exception e)
		{
			baseNum2 = 0;
		}
		baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));

		return (Double.parseDouble(num1) * baseNum + Double.parseDouble(num2) * baseNum) / baseNum;
	}

	/**
	 * 浮点数--减法
	 * 
	 * @author nge
	 * @date 2015年8月20日
	 * @param num1
	 * @param num2
	 * @return
	 * @throws Exception
	 */
	public static double MathMinus(String num1, String num2) throws Exception
	{
		double baseNum;
		int baseNum1, baseNum2;

		try
		{
			baseNum1 = num1.length() - num1.indexOf(".") - 1;
		}
		catch (Exception e)
		{
			baseNum1 = 0;
		}

		try
		{
			baseNum2 = num2.length() - num2.indexOf(".") - 1;
		}
		catch (Exception e)
		{
			baseNum2 = 0;
		}

		baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));

		return ((Double.parseDouble(num1) * baseNum - Double.parseDouble(num2) * baseNum) / baseNum);
	}

	/**
	 * 浮点数--乘法
	 * @author nge
	 * @date 2015年8月20日
	 * @param v1
	 * @param v2
	 * @return
	 * @throws Exception
	 */
	public static double MathMul(String v1, String v2) throws Exception
	{
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).doubleValue();
	}
	
	/**
	 * 浮点数--除法
	 * @author nge
	 * @date 2015年8月29日
	 * @param v1 
	 * @param v2
	 * @param scale 小数位数
	 * @return v1/v2
	 */
	public static double MathDiv(String v1,String v2,int scale) throws Exception{
	    if(scale<0)
	    {
	        throw new IllegalArgumentException(
	        "The scale must be a positive integer or zero");
	    }
	    BigDecimal b1 = new BigDecimal(v1);
	    BigDecimal b2 = new BigDecimal(v2);
	    return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 浮点数--四舍五入
	 * @author nge
	 * @date 2015年8月31日
	 * @param v
	 * @param scale
	 * @return
	 * @throws Exception
	 */
	public static double MathRound(String v,int scale) throws Exception{
	    if(scale<0)
	    {
	        throw new IllegalArgumentException(
	        "The scale must be a positive integer or zero");
	    }
	    BigDecimal b = new BigDecimal(v);
	    BigDecimal one = new BigDecimal("1");
	    return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static String CompanyNameFormat(String comaccount) throws Exception
	{
		if (StringUtil.isNullOrEmpty(comaccount))
		{
			return comaccount;
		}
		
		comaccount = comaccount.trim();
		comaccount = comaccount.replace("（", "(").replace("）", ")");
		return comaccount;
	}
	
	public static String replaceMatch(String v,String regex,String replace) throws Exception
	{
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(v);
		String result = m.replaceAll(replace).trim().toString();
		return result;
	}
	
	public static DataRow resultFormat(DataRow result)
	{
		if (MapUtil.isEmpty(result) || StringUtil.isNullOrEmpty(result.getString("errmsg")))
		{
			result = result == null?new DataRow():result;
			result.put("success", true);
		}
		else
		{
			result.put("success", false);
		}
		return result;
	}
	
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (candidates == null)
            return result;
        Arrays.sort(candidates);
        return combinationSum(candidates, target, 0);
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target, int start) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = start; i < candidates.length; i++) {
            if (i > start && candidates[i] == candidates[i - 1])
                continue;
            if (candidates[i] < target) {
                for (List<Integer> list : combinationSum(candidates, target - candidates[i], i)) {
                    if(!list.contains(candidates[i]))
                    {
                    	list.add(0, candidates[i]);
                    	result.add(list);
                    }
                }
            } else if (candidates[i] == target) {
                List<Integer> res = new ArrayList<>();
                res.add(candidates[i]);
                result.add(res);
            } else {
                break;
            }
        }
        return result;
    }
    
    public static void main(String[] args) throws ParseException {
//    	int[] nums = {1,2,4,8,16,32};
//    	int target = 31;
//    	System.out.println(new FunctionUtil().combinationSum(nums, target));
    }
}
