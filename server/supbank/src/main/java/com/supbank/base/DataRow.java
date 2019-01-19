package com.supbank.base;

import java.math.BigDecimal;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supbank.util.IgnoreCaseString;

public class DataRow<K, V> extends HashMap {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DataRow.class);
	private boolean upperflag = false;// 设置值是否为纯大写输出

	public boolean isUpperflag() {
		return upperflag;
	}

	/**
	 * 设置是否纯大写输出
	 */
	public void setUpperflag(boolean upperflag) {
		this.upperflag = upperflag;
	}

	public DataRow() {

	}

	/**
	 * 更换键值
	 * 
	 * @param sname
	 *            原来的键值
	 * @param dname
	 *            新键值
	 */
	public void transTo(String sname, String dname) {
		if (this.containsKey(sname) && this.get(sname) != null) {
			this.put(dname, this.get(sname));
			this.remove(sname);
		}
	}

	/**
	 * 判断字符串键值是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key) {
		return this.containsKey(new IgnoreCaseString(key));
	}

	/**
	 * 新增键值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public Object put(Object key, Object value) {
		if (key instanceof IgnoreCaseString) {
			super.put(key, value);
		} else if (key instanceof String) {
			super.put(new IgnoreCaseString((String) key), value);
		} else {
			super.put(new IgnoreCaseString(key.toString()), value);
		}
		return value;
	}

	/**
	 * 新增键值
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, String value) {
		if (value != null) {
			super.put(new IgnoreCaseString(key), value.trim());
		} else {
			super.put(new IgnoreCaseString(key), value);
		}
	}

	/**
	 * 根据字符串键值获取字符串value值
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		if (isUpperflag()) {
			if (this.containsKey(key)
					&& this.get(new IgnoreCaseString(key)) != null) {
				return this.get(new IgnoreCaseString(key)).toString()
						.toUpperCase();
			} else {
				return null;
			}
		} else {
			if (this.containsKey(key)
					&& this.get(new IgnoreCaseString(key)) != null) {
				return this.get(new IgnoreCaseString(key)).toString();
			} else {
				return null;
			}
		}
	}
	/**
	 * 根据字符串键值获取字符串value值，如果返回值为null，则返回默认值
	 * @param key
	 * @param defaultstr
	 * @return
	 */
	public String getString(String key,String defaultstr) {
		if (isUpperflag()) {
			if (this.containsKey(key)
					&& this.get(new IgnoreCaseString(key)) != null) {
				return this.get(new IgnoreCaseString(key)).toString()
						.toUpperCase();
			} else {
				return defaultstr;
			}
		} else {
			if (this.containsKey(key)
					&& this.get(new IgnoreCaseString(key)) != null) {
				return this.get(new IgnoreCaseString(key)).toString();
			} else {
				return defaultstr;
			}
		}
	}
	/**
	 * 根据字符串键值获取字符串value值，转换为大写
	 * 
	 * @param key
	 * @return
	 */
	public String getStringInUpper(String key) {
		String string = getString(key);
		if (string != null) {
			string = string.toUpperCase().trim();
		}
		return string;
	}

	/**
	 * 根据字符串键值获取整型value值
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		if (this.containsKey(key) && this.get(key) != null) {
			try {
				return (int) Double.parseDouble(this.get(key).toString().trim());
			} catch (Exception e) {
				return 0;
			}
		} else {
			return 0;
		}
	}

	/**
	 * 根据字符串键值获取整型value值
	 * 
	 * @param key
	 * @return
	 */
	public double getDouble(String key) {
		if (this.containsKey(key) && this.get(key) != null) {
			try {
				return Double.parseDouble(this.get(key).toString().trim());
			} catch (Exception e) {
				return 0;
			}
		} else {
			return 0;
		}
	}

	/**
	 * 根据字符串键值获取整型value值
	 * 
	 * @param key
	 * @return
	 */
	public BigDecimal getBigDecimal(String key) {
		if (this.containsKey(key) && this.get(key) != null) {
			try {
				return new BigDecimal(this.getString(key).trim());
			} catch (Exception e) {
				return new BigDecimal("0");
			}
		} else {
			return new BigDecimal("0");
		}
	}

	/**
	 * 根据对象键值获取对象value值
	 */
	@Override
	public Object get(Object key) {
		if (key instanceof IgnoreCaseString) {
			return super.get(key);
		} else {
			return super.get(new IgnoreCaseString(key.toString()));
		}
	}

	/**
	 * 判断对象键值是否存在
	 */
	@Override
	public boolean containsKey(Object key) {
		if (key instanceof IgnoreCaseString) {
			return super.containsKey(key);
		} else {
			return super.containsKey(new IgnoreCaseString(key.toString()));
		}
	}

	/**
	 * 根据字符串键值获取日期时间字符串，仅仅是截取长度不超过19位
	 * 
	 * @param key
	 * @return
	 */
	public String getDateTimeStr(String key) {
		if (super.containsKey(new IgnoreCaseString(key.toString()))
				&& super.get(new IgnoreCaseString(key.toString())) != null) {
			if (super.get(new IgnoreCaseString(key.toString())).toString().length() > 19) {
				return super.get(new IgnoreCaseString(key.toString())).toString().substring(0, 19);
			}
		}
		return "";
	}

	/**
	 * 根据字符串键值获取日期字符串，仅仅是截取长度不超过10位
	 * 
	 * @param key
	 * @return
	 */
	public String getDateStr(String key) {
		if (this.containsKey(key)
				&& this.get(key) != null) {
			if (this.get(key).toString().length() > 10) {
				return this.get(key).toString().substring(0, 10);
			}
		}
		return "";
	}

	/**
	 * 根据字符串键值获取时间字符串，仅仅是截取时间中的11--19位
	 * 
	 * @param key
	 * @return
	 */
	public String getTimeStr(String key) {
		if (super.containsKey(key.toUpperCase())
				&& super.get(key.toUpperCase()) != null) {
			if (super.get(key.toUpperCase()).toString().length() > 19) {
				return super.get(key.toUpperCase()).toString()
						.substring(11, 19);
			}
		}
		return "";
	}

	/**
	 * 键值集合
	 */
	public Set keySet() {
		Iterator items = super.keySet().iterator();
		HashSet hashset = new HashSet();
		while (items.hasNext()) {
			hashset.add(((IgnoreCaseString) items.next()).toString());
		}
		return hashset;
	}

	/**
	 * 删除主键
	 */
	@Override
	public V remove(Object key) {
		if (key instanceof IgnoreCaseString) {
			if(super.containsKey(key))
			return (V) super.remove(key);
		} else if (key instanceof String) {
			if(super.containsKey(new IgnoreCaseString((String) key)))
			return (V) super.remove(new IgnoreCaseString((String) key));
		} else {
			if(super.containsKey(new IgnoreCaseString(key.toString())))
			return (V) super.remove(new IgnoreCaseString(key.toString()));
		}
		return null;
	}

	/**
	 * 键值对集合
	 */
	public Set<Map.Entry<K, V>> entrySet() {
		Iterator items = super.entrySet().iterator();
		HashSet<Map.Entry<K, V>> hashset = new HashSet();
		while (items.hasNext()) {
			final Map.Entry entry = (java.util.Map.Entry) items.next();

			Map.Entry temp = new Map.Entry() {
				public Object getKey() {
					return entry.getKey().toString();
				}

				public Object getValue() {
					return entry.getValue();
				}

				public Object setValue(Object value) {
					return value;
				}
			};

			hashset.add(temp);
		}
		return hashset;
	}

	@Override
	public void putAll(Map map) {
		Iterator items = map.entrySet().iterator();
		while (items.hasNext()) {
			Map.Entry<K, V> entry = (Map.Entry) items.next();
			this.put(entry.getKey().toString(), entry.getValue());
		}
	}

	public void removeRowValue(String rowkey, String rowvalue)
	{
		Iterator items = this.entrySet().iterator();
		while (items.hasNext()) {
			Map.Entry<K, V> entry = (Map.Entry) items.next();
			if (entry.getValue() instanceof DataRow)
			{
				DataRow map = (DataRow)entry.getValue();
				if (map.getString(rowkey).equals(rowvalue))
				{
					this.remove(entry.getKey());
				}
			}
		}
	}

	/**
	 * 从集合中返回包含某个特定键值对DataRow
	 * @param key
	 * @return
	 */
	public static DataRow getItemFromCollection(Collection<DataRow> collection,String key,String value){

		Iterator<DataRow> iterable = collection.iterator();
		while (iterable.hasNext()){
			DataRow curDataRow = iterable.next();
			IgnoreCaseString keyobj = new IgnoreCaseString(key);
			if(curDataRow.containsKey(keyobj)){

				if(String.valueOf(curDataRow.get(keyobj)).equals(value)){
					return curDataRow;
				}else {
					continue;
				}
			}else {
				continue;
			}

		}

		return null;

	}
}
