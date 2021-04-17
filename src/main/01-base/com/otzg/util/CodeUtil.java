package com.otzg.util;

import com.otzg.log.util.LogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeUtil {

    /**
     * 把utf-8格式的字符串编码成urlEncode
     * @param str =中文エキサイト네이버 중국어사전18
     * @return %E4%B8%AD%E6%96%87%E3%82%A8%E3%82%AD%E3%82%B5%E3%82%A4%E3%83%88%EB%84%A4%EC%9D%B4%EB%B2%84+%EC%A4%91%EA%B5%AD%EC%96%B4%EC%82%AC%EC%A0%8418
     */
    public static String urlEncode(String str) {
        String urlEncode = "" ;
        try {
            urlEncode = URLEncoder.encode (str, "UTF-8" );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return urlEncode;
    }

    /**
     * urlEncode 编码的字符串解码成utf-8格式
     * @param str = %E4%B8%AD%E6%96%87%E3%82%A8%E3%82%AD%E3%82%B5%E3%82%A4%E3%83%88%EB%84%A4%EC%9D%B4%EB%B2%84+%EC%A4%91%EA%B5%AD%EC%96%B4%EC%82%AC%EC%A0%8418
     * @return 中文エキサイト네이버 중국어사전18
     */
    public static String urlDecode(String str) {
        String urlDecode = "" ;
        try {
            urlDecode = URLDecoder.decode (str, "UTF-8" );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return urlDecode;
    }


    /**
     * GBK转UTF-8
     * @param gbkStr
     * @return
     */
    public static String gbkToUtf8(String gbkStr) {
        try {
            return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return "";
    }

    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }
    /**
     * 以code编码字符串
     * @author G/2016-4-20 下午4:25:44
     * @param str
     * @param code 编码方式
     * @return
     */
    public final static String encode(String str,String code){
        String s = "";
        try {
            s = new String(str.getBytes(code),code);
        } catch (Exception e) {
            LogUtil.print(e.toString());
        }
        return s;
    }

    /**
     * 字符串解码
     * @author G/2016-4-20 下午4:25:44
     * @param str
     * @param code
     * @return
     */
    public final static String decode(String str,String code){
        String s = "";
        try {
            s = new String(str.getBytes(getEncoding(str)),code);
        } catch (Exception e) {
        }
        return s;
    }

    /**
     * 判断字符串如果是ISO-8859-1，转换成UTF-8解码
     * 有时要求new String 把ISO-8859-1转成utf-8 得到的是gb2312
     * 或者把gb2312转成utf-8
     * @author G/2017-07-14
     * @param str
     * @return UTF-8 编码
     */
    public final static String getUtf8(String str){
        String nstr=str;
        if(getEncoding(str).equals("ISO-8859-1")){
            nstr = decode(str,"GB2312");
            nstr = decode(nstr,"UTF-8");
        }
        if(getEncoding(nstr).equals("UTF-8")){
            nstr = decode(str,"UTF-8");
        }
        return nstr;
    }

    /**
     * 判断字符串编码
     * @author G/2016-1-12 上午9:14:28
     * @param str
     * @return
     */
    public final static String getEncoding(String str) {
        String encode[] = new String[]{
                "UTF-8",
                "ISO-8859-1",
                "GB2312",
                "GBK",
                "GB18030",
                "Big5",
                "Unicode",
                "ASCII"
        };
        for (int i = 0; i < encode.length; i++){
            try {
                if (str.equals(new String(str.getBytes(encode[i]), encode[i]))) {
                    return encode[i];
                }
            } catch (Exception ex) {
            }
        }
        return "";
    }

    /**
     * 判断字符串中是否有中文
     * @author G/2016年12月21日 上午9:53:05
     * @param str
     * @return
     */
    public final static boolean hasChineseChar(String str){
        boolean temp = false;
        Pattern p=Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m=p.matcher(str);
        if(m.find()){
            temp =  true;
        }
        return temp;
    }

    public static void main(String args[]){

        String c = "中文エキサイト네이버 중국어사전18";
        c=encode(c,"utf-8");
        LogUtil.print("url Encode="+c);

        String d = getUtf8(c);
        LogUtil.print("d="+d);
    }
}
