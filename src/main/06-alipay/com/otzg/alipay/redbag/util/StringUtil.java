package com.otzg.alipay.redbag.util;

/**
 * @ClassName: StringUtil
 * @Description: TODO(字符串验证工具)
 * @author 五方 wufang.lh
 * @date 2017年5月17日 下午3:26:57
 *
 */
public class StringUtil {

	/**
	 * 字符串不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 判断字符串为空
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		if (str == null || str.length() == 0 || str.trim() == "")
			return true;
		return false;
	}

	/**
	 * 将句点、下划线划分的字符串转换成驼峰形式，并将首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String transToFirstUpper(String str){
        if(str==null){
            return str;
        }
        String[] strs = str.split("\\.|_");
        StringBuilder sb = new StringBuilder();
        for(String s:strs){
            if(isNotBlank(s)){
                sb.append(s.substring(0,1).toUpperCase()).append(s.substring(1));
            }
        }

        return  sb.toString();
    }
	
	/**
	 * 将句点、下划线划分的字符串转换成为驼峰形式
	 * 
	 * @param str
	 * @return
	 */
    public static String transToFirstLower(String str){
        if(str==null){
            return str;
        }
        String[] strs = str.split("[\\.|_]");
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<strs.length;i++){
            String s = strs[i];
            if(isNotBlank(s)) {
                if(i==0){
                    sb.append(s.substring(0, 1).toLowerCase()).append(s.substring(1));
                }else{
                    sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
                }
            }
        }

        return  sb.toString();
    }
}
