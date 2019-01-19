package com.supbank.util;

import java.io.Serializable;

/**
 * HashCode忽略大小写
 * @author jiayl
 *
 */
public class IgnoreCaseString implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String key;
	
	public IgnoreCaseString(String key)
	{
		this.key =key;
	}
	@Override
	public int hashCode()
	{
		return key.toUpperCase().hashCode();
	}
	@Override
	public boolean equals(Object obj)
	{
		return obj.toString().equalsIgnoreCase(key);
	}
	@Override
	public String toString()
	{
		return key;
	}
}
