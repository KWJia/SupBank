package com.supbank.dao.service;

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
		int height = Integer.parseInt(params.getString("height"));
		String sql = "select * from td_block where flag=1 and height="+height;
		List<DataRow> blockList = dbService.queryForList(sql);
		if(blockList.isEmpty()) {
			result.put("ack", "error");
			result.put("errorMessage", "0 result");
			result.put("timeStamp", System.currentTimeMillis());
		}else {
			result.put("ack", "success");
			result.put("errorMessage", "");
			result.put("timeStamp", System.currentTimeMillis());
			result.put("blockList", blockList);
		}
		return result;
	}
	
	
}
