package com.otzg.wxpay.util;

import com.otzg.wxpay.util.service.WXPayConfig;
import javax.crypto.Cipher;
import javax.security.cert.X509Certificate;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.Base64;

/**
 * @author 13345
 * @create 2019-11-01 13:43
 * @desc RSA加密算法
 **/
public class RSAUtil {
    private static final String ALGORITHOM = "RSA";//固定值，无须修改

    private static final String CIPHER_PROVIDER = "SunJCE";
    private static final String TRANSFORMATION_PKCS1Paddiing = "RSA/ECB/PKCS1Padding";
    private static final String CHAR_ENCODING = "UTF-8";//固定值，无须修改

    //平台证书路径，开发人员需根据具体路径修改
    private static String PUBLIC_KEY_FILENAME;

    public static void setCertPath(String certPath){
        PUBLIC_KEY_FILENAME=certPath;
    }

    //对敏感内容（入参Content）加密，其中PUBLIC_KEY_FILENAME为存放平台证书的路径，
    // 平台证书文件存放明文平台证书内容，且为pem格式的平台证书（平台证书的获取方式参照平台证书及序列号获取接口，
    // 通过此接口得到的参数certificates包含了加密的平台证书内容ciphertext，然后根据接口文档中平台证书解密指引，
    // 最终得到明文平台证书内容）
    public static String rsaEncrypt(String Content) throws Exception {
        System.out.println("path="+PUBLIC_KEY_FILENAME);
        final byte[] PublicKeyBytes = Files.readAllBytes(Paths.get(PUBLIC_KEY_FILENAME));
        X509Certificate certificate = X509Certificate.getInstance(PublicKeyBytes);
        PublicKey publicKey = certificate.getPublicKey();
        return encodeBase64(encryptPkcs1padding(publicKey, Content.getBytes(CHAR_ENCODING)));
    }


    /**
     * 为了自己方便，多加个个传内容的，因为我解密后并没有保存到文件里，而是自己重新解密
     * 要问为什么？
     * 需求有多个服务商号，没办法
     * @param Content
     * @param certStr
     * @return
     * @throws Exception
     */
    public static String rsaEncryptByCert(String Content, String certStr) throws Exception {
        X509Certificate certificate = X509Certificate.getInstance(certStr.getBytes());
        PublicKey publicKey = certificate.getPublicKey();
        return encodeBase64(encryptPkcs1padding(publicKey, Content.getBytes(CHAR_ENCODING)));
    }





    //数据加密方法
    private static byte[] encryptPkcs1padding(PublicKey publicKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance(TRANSFORMATION_PKCS1Paddiing, CIPHER_PROVIDER);
        ci.init(Cipher.ENCRYPT_MODE, publicKey);
        return ci.doFinal(data);
    }
    //加密后的秘文，使用base64编码方法
    private static String encodeBase64(byte[] bytes) throws Exception {
        return Base64.getEncoder().encodeToString(bytes);
    }


    //对“hello world进行加密”
    public static void main(String[] args) {
        try {
            RSAUtil.setCertPath(WXPayConfig.getPublicCertPath());
            System.out.println( "after encrypt: " + rsaEncrypt("hello world"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
