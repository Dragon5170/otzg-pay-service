package com.otzg.util;


import com.otzg.log.util.LogUtil;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 *
 * @author IceWee
 * @date 2012-4-26
 * @version 1.0
 */
public class RsaUtil {
    /** */
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /** */
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /** */
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /** */
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data
     *            已加密数据
     * @param privateKey
     *            私钥(BASE64编码)
     *
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64.encode(signature.sign());
    }

    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data
     *            已加密数据
     * @param publicKey
     *            公钥(BASE64编码)
     * @param sign
     *            数字签名
     *
     * @return
     * @throws Exception
     *
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.decode(sign));
    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData
     *            已加密数据
     * @param privateKey
     *            私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData
     *            已加密数据
     * @param publicKey
     *            公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
        byte[] keyBytes = Base64.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data
     *            源数据
     * @param publicKey
     *            公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data
     *            源数据
     * @param privateKey
     *            私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap
     *            密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encode(key.getEncoded());
    }

    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap
     *            密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encode(key.getEncoded());
    }

    /**
     * java端公钥加密
     */
    public static String encryptedDataOnJava(String data, String PUBLICKEY) {
        try {
            data = Base64.encode(encryptByPublicKey(data.getBytes(), PUBLICKEY));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

    /**
     * java端私钥解密
     */
    public static String decryptDataOnJava(String data, String PRIVATEKEY) {
        String temp = "";
        try {
            byte[] rs = Base64.decode(data);
            temp = new String(RsaUtil.decryptByPrivateKey(rs, PRIVATEKEY),"UTF-8"); //以utf-8的方式生成字符串

        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static void main(String[] args) throws Exception {
		String privateKey ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKYoiGrhZKwBnAjWlNhrf+DFUfVkSKH8YFYPVUurYE1dmLEFrJnNb6/7HKV+SiBkQBr+WZoOHf4hWXHkXrMpPJAWGVMvJc+UL2m1ZW+njIcA096ElrQdvdXPQ50yacOY4Js70fomQI9VCmE/Mt3qXWhGp3Pp2o/sWaaa7chjZcfBAgMBAAECgYBYUDjZnfzVE3HsnKi2MsTgIeCC7g9Q0YQemb27H1ZrKHEsvZhUkwVm4rACIhKiDsan0kKriA9W8EDLMRdIYIWIMIMhN0MObRCvfu5pkepWaeOA+F+T+SL29ijc7GhmS5i1XXznLLKOHAb2tcNkoLTcKxU40sbITSXAuFIZpfvzxQJBANFAJBVDvvAVVRpynUzp7GQe7xmUlODFdAS1Q8HEa5hpdtiWIMWLrVZITfl3C+51vWmG6eQRutwmgmVvk3snn98CQQDLR8dvhAZlJ2LTxj6DFjEw/OiRviFwDlk2sDjQcxz1B5+TdzKYMFaw9RMKCp38kfXQGedDi0QkVEm6E56GLQxfAkEAp3jSaEKEOtqX9kbtJnXCQI+RhcOpNAxUQsBgrmBqTN17xPTC3dhgrsHHxnVFE2Ega6kS4Ppft3sKueyG+PZJuQJAW18uQ+/iOAGWKH7Jhn6pKc3kc+40dXvdmfln8Dpt363Hiq9fbIz9ypi+MBtJnEe3aAzcqL2mqXXBlgRPkxwYnQJBAMOTGdbzd16Rl8yRW+CEj5OSSRzdYPuuiFv0LW/mheITfsm7X/gm3IJ/v6/MyUP2xyAEXDh6vufNlcQUzCPgLUM=";
		String pubicKey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmKIhq4WSsAZwI1pTYa3/gxVH1ZEih/GBWD1VLq2BNXZixBayZzW+v+xylfkogZEAa/lmaDh3+IVlx5F6zKTyQFhlTLyXPlC9ptWVvp4yHANPehJa0Hb3Vz0OdMmnDmOCbO9H6JkCPVQphPzLd6l1oRqdz6dqP7Fmmmu3IY2XHwQIDAQAB";
        Map<String,Object> jo = new HashMap<>();
        jo.put("aaa","xxxxxxxxxxx");
        jo.put("bbb","qqwefqwefsdf1212");
        String sign = RsaUtil.sign(jo.toString().getBytes(),privateKey);
        LogUtil.print("sign="+sign);
        boolean b = RsaUtil.verify(jo.toString().getBytes(),pubicKey,sign);
        LogUtil.print("r="+b);
    }
}