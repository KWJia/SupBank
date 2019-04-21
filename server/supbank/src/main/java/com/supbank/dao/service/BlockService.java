package com.supbank.dao.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supbank.base.DBService;
import com.supbank.base.DataRow;

/**
 * Block的Service
 * @author kwj19
 *
 */
@Service
public class BlockService {

	public static final Logger logger = LoggerFactory.getLogger(BlockService.class);
	
	@Autowired
	private DBService dbService;
	
	/**通过id查询block详细信息
	 * 
	 * @param request
	 * @param params
	 * @return
	 */
	public DataRow getBlockInfoById(HttpServletRequest request, DataRow params) {
		DataRow result = new DataRow();
		String hash = params.getString("hash");
		String sql = "select * from td_block where flag=1 and hash='"+hash+"'";
		List<DataRow> blockList = dbService.queryForList(sql);
		if(blockList.isEmpty()) {
			result.put("status", 1);
			result.put("errorMessage", "0 result");
		}else {
			result.put("status", 0);
			result.put("blockList", blockList);
		}
		return result;
	}
	
	/**
	 * 获取最长合法链
	 * @param request
	 * @return
	 */
	public DataRow getLongestLegalChain(HttpServletRequest request) {
		DataRow result = new DataRow();
		List<DataRow> heightestBlockList = dbService.queryForList("select hash,prehash,height,age,transactionnumber,miner,size from td_block where flag=1 and islegal=1 and height =(select max(height) from td_block)");

		List<DataRow> longestLegalChain = new ArrayList<DataRow>();
		for (DataRow dataRow : heightestBlockList) {

			do {
				longestLegalChain.add(dataRow);
				String prehash = dataRow.getString("prehash");
				String sql = "select hash,prehash,height,age,transactionnumber,miner,size from td_block where flag=1 and islegal=1 and hash='"
						+ prehash + "'";
				try {
					dataRow = dbService.querySimpleRowBySql(sql);
					if(dataRow.getString("prehash")==null) {
						longestLegalChain.add(dataRow);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			} while (dataRow.getString("prehash")!=null);
		}
		
		result.put("status", 0);
		result.put("longestLegalChain", longestLegalChain);
		
		return result;
		
	}
	
	
	
	
	
	
	
}
