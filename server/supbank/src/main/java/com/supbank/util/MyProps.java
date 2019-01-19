package com.supbank.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "project")
public class MyProps {
	//风帆公众号的appid
	private String appid;
	private String appsecret;
	// 绑定域名
	private String domainurl;
	// excel上传文件存放目录
	private String excelfilepath;
	// 上传文件存放目录
	private String filepath;
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public String getDomainurl() {
		return domainurl;
	}
	public void setDomainurl(String domainurl) {
		this.domainurl = domainurl;
	}
	public String getExcelfilepath() {
		return excelfilepath;
	}
	public void setExcelfilepath(String excelfilepath) {
		this.excelfilepath = excelfilepath;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
}
