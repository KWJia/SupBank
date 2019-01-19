package com.supbank.base;

/*
 * 分页参数初始化服务类
 */
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import com.supbank.util.DateTimeUtil;
import com.supbank.util.MapUtil;
import com.supbank.util.UserUtil;


@Service
public class ResultByPageService
{
	private static final Logger logger = LoggerFactory.getLogger(ResultByPageService.class);

	// 初始化提交参数,分页查询
	public ResultByPage getByRequest(HttpServletRequest request)
	{
		ResultByPage rbp = new ResultByPage();
		Enumeration pnames = request.getParameterNames();
		//logger.debug("request.getParameterMap() -->" + request.getParameterMap());
		while (pnames.hasMoreElements())
		{
			String name = pnames.nextElement().toString();
			String value;
			try
			{
				value = URLDecoder.decode(request.getParameter(name), "UTF-8");
				//logger.debug("name -->" + name + "   value-->" + value);
				// 页面转向参数处理
				if (name.toLowerCase().equals("topagenum") && !value.equals(""))
				{
					rbp.setPagenum(Integer.valueOf(value));
					continue;
				}
				// 每页展示数量参数处理
				if (name.toLowerCase().equals("perpagenum") && !value.equals(""))
				{
					rbp.setPerpage(Integer.valueOf(value));
					continue;
				}
				// 每页展示数量Post参数处理
				if (name.toLowerCase().endsWith("perpagenum") && !value.equals(""))
				{
					rbp.setPerpage(Integer.valueOf(value));
					continue;
				}
				// 是否导出
				if (name.toLowerCase().endsWith("exportflag") && !value.equals(""))
				{
					rbp.setExportflag(Boolean.valueOf(value));
					continue;
				}
				// POST参数忽略处理
				if (name.toLowerCase().endsWith("hidden_condition"))
				{
					continue;
				}
				// 排序参数处理
				if (name.toLowerCase().endsWith("orderby"))
				{
					String[] sortfield = value.split("\\|");
					if (sortfield.length == 2)
					{
						rbp.getOrderbyfield().put(sortfield[0], sortfield[1]);
					}
					continue;
				}
				rbp.getCondition().put(name, value);
			}
			catch (UnsupportedEncodingException e1)
			{
				e1.printStackTrace();
			}

		}
		
		DataRow onlinestaff = UserUtil.getOnlineStaff(request);
		if (!MapUtil.isEmpty(onlinestaff))
		{
			if (onlinestaff.containsKey("staff"))
			{
				DataRow staffInfo = (DataRow)onlinestaff.get("staff");
				rbp.getCondition().put("schooid", staffInfo.getString("schooid"));
			}
			else
			{
				rbp.getCondition().put("schooid", onlinestaff.getString("schooid"));
			}
		}

		return rbp;
	}

	// 自动完成参数初始化
	public ResultByPage getByRequestByAuto(HttpServletRequest request)
	{
		ResultByPage rbp = new ResultByPage();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements())
		{
			String name = names.nextElement();
			String value = request.getParameter(name);
			//logger.debug("name -->" + name + "   value-->" + value);
			if (name.endsWith("_input"))
			{
				String inputvalue;
				try
				{
					inputvalue = URLDecoder.decode(value, "UTF-8");
					rbp.getCondition().put(name.substring(0, name.lastIndexOf("_input")),
							inputvalue);
				}
				catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				}
				continue;
			}
			rbp.getCondition().put(name, value);
		}
		//logger.debug(rbp.getCondition().toString());
		return rbp;
	}

	/**
	 * 自动完成参数初始化
	 * 
	 * @param request
	 * @param showidParam
	 * @param sqlParam
	 * @return
	 */
	public ResultByPage getByRequestByAutoForRepeat(HttpServletRequest request, String showidParam,
			String sqlParam)
	{
		ResultByPage rbp = new ResultByPage();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements())
		{
			String name = names.nextElement();
			String value = request.getParameter(name);
			//logger.debug("name -->" + name + "   value-->" + value);
			if (name.endsWith("_input"))
			{
				String inputvalue;
				try
				{
					inputvalue = URLDecoder.decode(value, "UTF-8");
					if (showidParam != null && !showidParam.isEmpty()
							&& name.startsWith(showidParam))
					{
						rbp.getCondition().put(sqlParam, inputvalue);
					}
					else
					{
						rbp.getCondition().put(name.substring(0, name.lastIndexOf("_input")),
								inputvalue);
					}

				}
				catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				}
				continue;
			}
			rbp.getCondition().put(name, value);
		}

		//logger.debug(rbp.getCondition().toString());
		return rbp;
	}

	// 初始化提交参数
	public DataRow getParamByRequest(HttpServletRequest request)
	{
		DataRow param = new DataRow();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements())
		{
			String key = names.nextElement();
			try
			{
				if (request.getParameter(key).contains("+"))
				{
					param.put(key, request.getParameter(key));
				}
				else
				{
					param.put(key, URLDecoder.decode(request.getParameter(key).replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "UTF-8"));
				}
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		DataRow onlinestaff = UserUtil.getOnlineStaff(request);
		if (onlinestaff != null)
		{
			param.put("lgstaffid", onlinestaff.getString("staffid"));
			param.put("lgstaffname", onlinestaff.getString("staffname"));
			param.put("lgcompanyid", onlinestaff.getString("companyid"));
		}
		return param;
	}

	public void InitCommonParam(HttpServletRequest request, DataRow param)
	{
		DataRow onlinestaff = UserUtil.getOnlineStaff(request);
		if (onlinestaff != null)
		{
			param.put("updatestaffid", onlinestaff.getString("staffid"));
			param.put("updatestaffname", onlinestaff.getString("staffname"));
			if (onlinestaff.containsKey("hostid"))
			{
				param.put("hostid", onlinestaff.getString("hostid"));
			}
			param.put("updatetime", DateTimeUtil.getNowDateStr());
		}
	}

	public DataRow getOnlineStaffInfo(HttpServletRequest request) throws Exception
	{
		DataRow param = new DataRow();
		DataRow onlinestaff = UserUtil.getOnlineStaff(request);
		if (onlinestaff == null)
		{
			return null;
		}
		return onlinestaff;
	}
}
