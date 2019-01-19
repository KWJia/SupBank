package com.supbank.base;
/*
 * 分页查询结果与条件
 */
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ResultByPage {
	private static final Logger logger = LoggerFactory.getLogger(ResultByPage.class);
	private String sql;
	private List<DataRow> resultlist;//结果集
	private List<DataRow> fieldlist;//展示字段名
	private List<String> showpagenum;//展示页码
	private DataRow condition;//条件
	private DataRow orderbyfield;
	private boolean exportflag=false;//是否导出
	private int allcount=0;//共多少条记录
	private int perpage=10;//每页展示条数
	private int allpage=0;//共多少页
	private int pagenum=1;//当前页
	private int start=0;
	private int end=0;
	
	public boolean isExportflag() {
		return exportflag;
	}

	public void setExportflag(boolean exportflag) {
		this.exportflag = exportflag;
	}
	
	public String getOrderbyfieldStr() {
		StringBuffer condstr = new StringBuffer();
		for(Object obj :orderbyfield.keySet())
		{
			condstr.append("&orderby=").append(obj).append("|").append(orderbyfield.get(obj).toString());
		}
		return condstr.toString();
	}
	
	public DataRow getOrderbyfield() {
		return orderbyfield;
	}

	public void setOrderbyfield(DataRow orderbyfield) {
		this.orderbyfield = orderbyfield;
	}

	public ResultByPage()
	{
		resultlist = new ArrayList<DataRow>();
		this.fieldlist = new ArrayList<DataRow>();
		showpagenum = new ArrayList<String>();
		condition = new DataRow();
		orderbyfield = new DataRow();
	}
	
	//查询条件
	public DataRow getCondition() {
		return condition;
	}
	
	//查询条件字符串
	public String getConditionStr()
	{
		StringBuffer condstr = new StringBuffer();
		if(condition.size()>0)
		{
			for(Object obj :condition.keySet())
			{
				if(obj!=null)
				{
					condstr.append("&").append(obj).append("=").append(condition.getString(obj.toString()));
				}
			}
			return condstr.toString();
		}
		return "";
	}
	//查询条件
	public void setCondition(DataRow condition) {
		this.condition = condition;
	}
	
	public List<String> getShowpagenum() {
		showpagenum.clear();
		for(int i=getShowStartPage();i<=getShowEndPage();i++)
		{
			showpagenum.add(String.valueOf(i));
		}
		return showpagenum;
	}
	/*
	 * 展示页的起始页码
	 */
	public int getShowStartPage()
	{
		if(pagenum>2)
		{
			return pagenum-2;
		}else{
			return 1;
		}
	}
	/*
	 * 展示页的结束页码
	 */
	public int getShowEndPage()
	{
		if(pagenum>2)
		{
			if(allpage-pagenum>2)
			{
				return pagenum+2;
			}else{
				return allpage;
			}
		}else{
			if(allpage-pagenum>=5)
			{
				return 5;
			}else{
				return allpage;
			}
		}
	}
	/*
	 * 获取上一页的页码 为0时即为不可上翻页
	 */
	public int getPrePagenum()
	{
		return pagenum-1;
	}
	/*
	 * 获取下一页的页码 为0时即为不可下翻页
	 */
	public int getNextPagenum()
	{
		if(pagenum<=allpage)
		{
			return pagenum+1;
		}else{
			return 1;
		}
	}
	/*
	 * 获取最后页的页码
	 */
	public int getLastPagenum()
	{
		return allpage;
	}
	/*
	 * 页面展示的开始记录
	 */
	public int getFstart() {
		return start+1;
	}
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		if(end>allcount)
		{
			return allcount;
		}
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		if(sql.indexOf("SQL_CALC_FOUND_ROWS")<0&&sql.toLowerCase().startsWith("select"))
		{
			int start = sql.toLowerCase().indexOf("select")+7;
			this.sql = "select SQL_CALC_FOUND_ROWS "+sql.substring(start);
		}else{
			this.sql = sql;
		}
	}
	public List<DataRow> getResultlist() {
		return resultlist;
	}
	public void setResultlist(List<DataRow> list) {
		this.resultlist = list;
	}
	public List<DataRow> getFieldlist() {
		return fieldlist;
	}
	public void setFieldlist(List<DataRow> fieldlist) {
		if(fieldlist==null||fieldlist.isEmpty()) return;
		for(DataRow map :fieldlist)
		{
			DataRow titlecol = new DataRow();
			titlecol.putAll(map);
			if(orderbyfield!=null&&orderbyfield.containsKey(titlecol.getString("fieldid")))
			{
				if(orderbyfield.getString(titlecol.getString("fieldid")).equals("asc"))
				{//升序
					titlecol.put("sortflag",3);
				}else if(orderbyfield.getString(titlecol.getString("fieldid")).equals("desc")){//降序
					titlecol.put("sortflag",4);
				}
			}
			this.fieldlist.add(titlecol);
		}
		
	}
	public int getAllcount() {
		return allcount;
	}
	public void setAllcount(int allcount) {
		this.allcount = allcount;
		if(allcount%perpage>0)
		{
			allpage=(allcount/perpage)+1;
		}else{
			allpage=allcount/perpage;
		}
		end = start +perpage;
	}
	public int getPerpage() {
		return perpage;
	}
	public void setPerpage(int perpage) {
		this.perpage = perpage;
		start = (pagenum-1)*perpage;
	}
	public int getAllpage() {
		return allpage;
	}
	public void setAllpage(int allpage) {
		this.allpage = allpage;
	}
	public int getPagenum() {
		return pagenum;
	}
	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
		start = (pagenum-1)*perpage;
	}

	/**
	 * 是否进行查询
	 * @author nge
	 * @date 2017年1月19日
	 * @return true:执行查询，false:不执行查询，只返回空页面
	 */
	public boolean queryFlag()
	{
		if (!this.getCondition().containsKey("initflag")||"true".equalsIgnoreCase(this.getCondition().getString("initflag")))
		{
			return true;
		}
		return false;
	}
	
	
}
