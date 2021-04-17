/**
 * 
 */
package com.otzg.util;

import com.otzg.log.util.LogUtil;

import java.security.*;
import java.util.HashMap;
import java.util.Map;

/**
 *  @author G/2016年9月29日
 *
 */
public class Generater{
	//平台统一合作者pid头部
	private static final String PHEAD = "2022";
	public static final String ALGORITHM = "SHA256withRsa";
	public static final String PUBLIC_KEY = "public_key";
	public static final String PRIVATE_KEY = "private_key";

	/**
	 * 给每一个合作者生成一个用户的pid
	 * 用来访问平台的数据资源
	 * @author G/2016年9月29日 下午5:50:07
	 * @return
	 */
	public static final String getAppId(){
		//md5日期时间
		return PHEAD+getNonce(DateUtil.yyyymmdd()+DateUtil.hhmmss(),16);
	}
	/**
	 * 生成一个用户的key值
	 * @author G/2016年9月29日 下午5:47:17
	 * @return
	 */
	public static final String getSecret(){
		return getNonce(""+getRandom(),16);
	}

	/**
	 * 获取随机字符串
	 * @author G/2016年10月17日 上午11:03:04
	 * @param sourceStr
	 * @param l =16则获取16位
	 * @return
	 */
	public final static String getNonce(String sourceStr,int l) {
		String result=AesUtil.MD5(sourceStr);
		if(l==16){
			result=result.substring(8,24);
		}
		return result;
	}

	/**
	  * 生成公钥私钥对
	  * @author G/2016年9月28日 下午5:51:41
	  * 调用方法:  Generater.getRsaKeyMap("434443434");
	  * 获取公匙:  Generater.getPublicKey();
	  * 获取私匙:  Generater.getPrivateKey();
	  */
	public static final Map<String,String> getRsaKeyMap(final String key){
		Map<String,String> map = new HashMap<>();
		try{
			KeyPairGenerator keygen = KeyPairGenerator.getInstance(ALGORITHM);
			SecureRandom secrand = new SecureRandom();
			secrand.setSeed(key.getBytes());
			keygen.initialize(1024,secrand);
			KeyPair keys = keygen.genKeyPair();
			PublicKey publicKey = keys.getPublic();   
			PrivateKey privateKey = keys.getPrivate();
			// 得到公钥字符串  
			map.put(PUBLIC_KEY,Base64.encode(publicKey.getEncoded()));
			// 得到私钥字符串  
			map.put(PRIVATE_KEY,Base64.encode(privateKey.getEncoded()));
			return map;
		}catch(Exception e){   
			return map;
		}
	}

	//获取6位随机数字符串
	private static final int getRandom(){
		return (int)(Math.random()*900000)+100000;
	}
	
	public static void main(String[] args) throws Exception {
//		Map<String,String> map = Generater.getRsaKeyMap("7186b1e96b7847d5");
//		LogUtil.print(PUBLIC_KEY+"="+map.get(PUBLIC_KEY));
//		LogUtil.print(PRIVATE_KEY+"="+map.get(PRIVATE_KEY));
//
//
//		JSONObject jo = new JSONObject();
//		jo.put("aaa","xxxxxxxxxxx");
//		jo.put("bbb","qqwefqwefsdf1212");
//		String bbb = RsaUtil.sign(jo.toString().getBytes(),map.get(PRIVATE_KEY));
//		LogUtil.print("sign="+bbb);
//		boolean b = RsaUtil.verify(jo.toString().getBytes(),map.get(PUBLIC_KEY),bbb);
//		LogUtil.print("r="+b);

//		String a="{\"a\":\"123\"}";
//		JSONObject jo = new JSONObject();
//		jo.put("a","123");
//		String pri="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKYoiGrhZKwBnAjWlNhrf+DFUfVkSKH8YFYPVUurYE1dmLEFrJnNb6/7HKV+SiBkQBr+WZoOHf4hWXHkXrMpPJAWGVMvJc+UL2m1ZW+njIcA096ElrQdvdXPQ50yacOY4Js70fomQI9VCmE/Mt3qXWhGp3Pp2o/sWaaa7chjZcfBAgMBAAECgYBYUDjZnfzVE3HsnKi2MsTgIeCC7g9Q0YQemb27H1ZrKHEsvZhUkwVm4rACIhKiDsan0kKriA9W8EDLMRdIYIWIMIMhN0MObRCvfu5pkepWaeOA+F+T+SL29ijc7GhmS5i1XXznLLKOHAb2tcNkoLTcKxU40sbITSXAuFIZpfvzxQJBANFAJBVDvvAVVRpynUzp7GQe7xmUlODFdAS1Q8HEa5hpdtiWIMWLrVZITfl3C+51vWmG6eQRutwmgmVvk3snn98CQQDLR8dvhAZlJ2LTxj6DFjEw/OiRviFwDlk2sDjQcxz1B5+TdzKYMFaw9RMKCp38kfXQGedDi0QkVEm6E56GLQxfAkEAp3jSaEKEOtqX9kbtJnXCQI+RhcOpNAxUQsBgrmBqTN17xPTC3dhgrsHHxnVFE2Ega6kS4Ppft3sKueyG+PZJuQJAW18uQ+/iOAGWKH7Jhn6pKc3kc+40dXvdmfln8Dpt363Hiq9fbIz9ypi+MBtJnEe3aAzcqL2mqXXBlgRPkxwYnQJBAMOTGdbzd16Rl8yRW+CEj5OSSRzdYPuuiFv0LW/mheITfsm7X/gm3IJ/v6/MyUP2xyAEXDh6vufNlcQUzCPgLUM=";
//		String pub="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmKIhq4WSsAZwI1pTYa3/gxVH1ZEih/GBWD1VLq2BNXZixBayZzW+v+xylfkogZEAa/lmaDh3+IVlx5F6zKTyQFhlTLyXPlC9ptWVvp4yHANPehJa0Hb3Vz0OdMmnDmOCbO9H6JkCPVQphPzLd6l1oRqdz6dqP7Fmmmu3IY2XHwQIDAQAB";
//		String bbb = RsaUtil.sign(jo.toString().getBytes(),pri);
//		LogUtil.print("sign="+bbb);
//		boolean c = RsaUtil.verify(jo.toString().getBytes(),pub,bbb);
//		LogUtil.print("sign="+c);


		LogUtil.print("appid="+getAppId());
		LogUtil.print("secret="+getSecret());

	}
}

