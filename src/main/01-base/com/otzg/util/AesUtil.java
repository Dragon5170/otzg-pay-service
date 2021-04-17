package com.otzg.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 对称加密方法
 */
public class AesUtil {

    final static String key="A0391DBF25247BE1F2B5C51B3CC";

    /**
     * MD5加密
     * @author G_2017年12月19日 下午2:31:10
     * @param sourceStr
     * @return
     */
    public final static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes("utf-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0){
                    i += 256;
                }else if (i < 16){
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *   根据参数生成KEY
     *   @param strKey
     *   @date 2018-12-12
     */
    private final static Key getAesKey(String strKey){
        try {
            //解决javax.crypto.BadPaddingException:
            // Given final block not properly padded
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(strKey.getBytes());

            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            return new SecretKeySpec(enCodeFormat, "AES");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    /**
     *   加密String明文输入,String密文输出
     *   @param   strMing
     *   @return
     */
    public final static String getEnCode(String strMing){
        String strMi = "";
        try{
            strMi = byte2hex(getEnCode(strMing.getBytes(),key));
        }catch(Exception e){
            e.printStackTrace();
        }
        return strMi;
    }
    public final static String getDeCode(String strMi){
        try{
            return new String(getAesCode(hex2byte(strMi.getBytes()),key));
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     *   加密以byte[]明文输入,byte[]密文输出
     *   @param   byteS
     *   @return
     */
    public final static byte[] getEnCode(byte[] byteS,String strKey){
        byte[] byteFina=null;
        try{
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE,getAesKey(strKey));
            byteFina = cipher.doFinal(byteS);
        }catch(Exception e){
            e.printStackTrace();
        }
        return byteFina;
    }
    /**
     *   解密以byte[]密文输入,以byte[]明文输出
     *   @param   byteD
     *   @return
     */
    private final static byte[] getAesCode(byte[] byteD,String strKey){
        byte[] byteFina=null;
        try{
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,getAesKey(strKey));
            byteFina = cipher.doFinal(byteD);
        }catch(Exception e){
            e.printStackTrace();
        }
        return byteFina;
    }
    /**
     * 二行制转字符串
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b){   //一个字节的数，
        // 转成16进制字符串
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            //整数转成十六进制表示
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();   //转成大写
    }

    public static byte[] hex2byte(byte[] b) {
        if((b.length%2)!=0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        byte[] b2 = new byte[b.length/2];
        for (int n = 0; n < b.length; n+=2) {
            String item = new String(b,n,2);
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            b2[n/2] = (byte)Integer.parseInt(item,16);
        }
        return b2;
    }

//    public static void main(String[] args){
//
//        //加密字符串,返回String的密文
//        String strEnc = getEnCode("{\"header\":\"中文\",\"style\":\"sv01\",\"bad\":true}",key);
//        System.out.println(strEnc);
//        //把String类型的密文解密
//        String strAes = getDeCode(strEnc,key);
//        System.out.println(strAes);
//    }
}
