package com.supbank.base;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "PageService")
@Transactional
public class PageService {
	
	private static final Logger logger = LoggerFactory.getLogger(PageService.class);
	
	@Autowired
	private DBService dbService;
	
	public int countTotal(String tablename,String codition) throws Exception{
		String sql = "select count(*) from " + tablename + " where 1=1 " + codition;
		DataRow count = dbService.querySimpleRowBySql(sql);
		int total = Integer.parseInt(count.getString("count(*)")); 
		return total;

	}
	
	/**
	 * @param content：查询内容；tablename：表名；codition：查询条件；current：当前页；pagesize：每页展示数
	 */
	public List<DataRow> queryByPage(String content,String tablename,String codition,int current,int pagesize){
		String sql = "select " + content + " from " + tablename + " where 1=1 " + codition + " limit " + ((current - 1) * pagesize) + "," + pagesize;
		return dbService.queryForList(sql);
	}
	
}
