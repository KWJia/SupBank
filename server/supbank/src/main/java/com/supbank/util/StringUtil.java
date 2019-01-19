package com.supbank.util;

public class StringUtil {
	public static String arrayStrAddSplit(String str, String splitstr) {
		String restr = "";
		String[] strarray = str.split(splitstr);
		for (String item : strarray) {
			if (item.isEmpty()) {
				continue;
			}
			restr = restr + "'" + item + "',";
		}

		if (restr.length() > 0
				&& (restr.lastIndexOf(splitstr) == (restr.length() - 1))) {
			restr = restr.substring(0, restr.length() - 1);
		}
		return restr;
	}

	public static String join(Object[] array, char separator) {
		if (array == null) {
			return null;
		}
		int arraySize = array.length;
		int bufSize = (arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0]
				.toString().length()) + 1) * arraySize);
		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = 0; i < arraySize; i++) {
			if (i > 0) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	public static boolean isNullOrEmpty(String string) {
		if (string == null || string.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 格式化浮点数，小数点后几位
	 * @param 被格式化数据
	 * @param 小数点后位数
	 * @return
	 */
	public static String formatDoubleAfterPoint(String data, int length) {
		double d =Double.parseDouble(data);
		return String.format("%-10." + length + "f", d).trim();
	}
	
	/**
	 * 右填充对齐
	 * @author nge
	 * @date 2015年8月31日
	 * @param v
	 * @param replace 填充字符
	 * @param totalLength 填充后总长度
	 * @return
	 */
	public static String padRight(String v, char replace, int totalLength)
    {
        if (v == null)
        {
            v = "";
        }
        int det = totalLength - v.length();

        if (det <= 0)
        {
            return v.substring(0, totalLength);
        }

        for (int i = 0; i < det; i++)
        {
            v = v + replace;
        }

        return v;
    }
	
	/**
     * 左填充对齐
     * 
     * @author nge
     * @date 2015年8月31日
     * @param v
     * @param replace
     *            填充字符
     * @param totalLength
     *            对齐后总长度
     * @return
     */
    public static String padLeft(String v, char replace, int totalLength)
    {
        if (v == null)
        {
            v = "";
        }

        int det = totalLength - v.length();

        if (det <= 0)
        {
            return v.substring(0, totalLength);
        }

        for (int i = 0; i < det; i++)
        {
            v = replace + v;
        }

        return v;
    }
    
    public static boolean checkAscciiChar(String txt)
    {
    	for (int i = 0; i < txt.length(); i++) {  
            int cp = txt.codePointAt(i);  
            if (cp==9||cp>127) {  
                return false;  
            }  
        }  
    	return true;
    }
}
