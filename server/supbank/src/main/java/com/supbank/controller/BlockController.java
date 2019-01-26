package com.supbank.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.supbank.base.DataRow;
import com.supbank.dao.service.BlockService;
import com.supbank.util.JsonUtil;

@RestController
@RequestMapping("/block")
public class BlockController {

	@Autowired
	private BlockService blockService;
	
	
	/**
	 * 获取block详细信息
	 * @param request
	 * @param params
	 * @return
	 */
	@CrossOrigin
	@ResponseBody
	@PostMapping("getBlockById")
	public String getBlockInfo(HttpServletRequest request,@RequestBody DataRow<String,String> params) {
		DataRow result = null;
		result = blockService.getBlockInfoById(request, params);
		return JsonUtil.resultJsonString(result);
	}
	
}
