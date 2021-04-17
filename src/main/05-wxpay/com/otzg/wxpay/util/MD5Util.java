package com.otzg.wxpay.util;


import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class MD5Util {

	/**
	 * 对上次文件进行MD5获取其Hash值
	 * @param file
	 * @return
	 */
	public static String md5HashCode(File file) {
		try {
            InputStream fis =new FileInputStream(file);
			MessageDigest MD5 = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[8192];
			int length;
			while ((length = fis.read(buffer)) != -1) {
				MD5.update(buffer, 0, length);
			}
			return new String(Hex.encodeHex(MD5.digest()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String encode(String origin) {
		return MD5Encode(origin,"UTF-8");
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString.toUpperCase();
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

}
