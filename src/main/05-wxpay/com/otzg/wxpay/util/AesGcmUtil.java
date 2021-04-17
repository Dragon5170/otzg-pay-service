package com.otzg.wxpay.util;

import com.otzg.util.Base64;
import com.otzg.util.FuncUtil;
import com.otzg.wxpay.util.service.WXPayConfig;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于微信下载的证书进行解密
 */
public class AesGcmUtil {
    /**
     * 获取微信证书
     * 参考微信地址：
     * https://pay.weixin.qq.com/wiki/doc/api/xiaowei.php?chapter=19_11
     * <p>
     * data里面的serial_no，我们申请入驻需要的参数就是这个
     * 其次是encrypt_certificate，主要用来平台证书解密成密钥，为申请入驻接口敏感信息加密处理,后面申请入驻时详细讲解
     * <p>
     * 获取如下三个参数
     * associated_data，
     * nonce，
     * ciphertext
     */
    public final static String getCertFicates() {
        // 初始化一个HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // Post请求
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/risk/getcertficates");
        /**
         * 这边需要您提供微信分配的商户号跟API密钥
         */
        Map<String, String> param = new HashMap<>();
        param.put("mch_id", WXPayConfig.getMchId());
        param.put("nonce_str", FuncUtil.getNonce("nonce", 16));
        // 暂只支持HMAC-SHA256 加密
        param.put("sign_type", "HMAC-SHA256");
        // 对你的参数进行加密处理
        param.put("sign", SHAUtil.wechatCertficatesSignBySHA256(param, WXPayConfig.getKey()));
        httpPost.setEntity(new StringEntity(XmlUtil.Map2Xml(param), "UTF-8"));
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_XML.getMimeType());
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            System.out.println("获取平台证书响应 =" + httpResponse);
            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200) {
                String responseEntity = EntityUtils.toString(httpResponse.getEntity());
                Map<String, Object> m = XmlUtil.parse(responseEntity);
                System.out.println("获取平台证书响应 =" + m.toString());
                //2019-11-15 调接口返回=>
                // 获取平台证书响应 ={nonce_str=MWQ5hPcwyB4YuJ9Y,
                // certificates={"data":[{"serial_no":"749535475D89DF9ECF413A6CA05F74FF4CEDDCB8","effective_time":"2019-05-06 16:45:20","expire_time":"2024-05-04 16:45:20","encrypt_certificate":{"algorithm":"AEAD_AES_256_GCM","nonce":"c4fce6dde537","associated_data":"certificate","ciphertext":"QY4v5Y2nBEXMmgJDWolonYzH+ML/JQyr+AHZMrLw7DI/J/19V19e1bMQQ6xnah3K/sZbG2p2cTgJGzJHcoesqE7/+IwLqtbA9gup872x6oNBo6ZtPqbdHexy7J5XV99l2jH9ioQaoD+A+I1b2ZLx6LedTW2NPLRj0zMabYYNol24IR7ZiiPUQLOclNJ5uOta4Wvwlc94FuPyRtmih0/bPiOu0AsNySrYLhE5E87jWzsQQR/E9Qozcaul+Z6t3rr01K8oV3z6y0S5GOQoeZqBS8iZhmEO5KMpN/6v+V3Q/URdoXodvU8gxzJZGSQj+AAklPB+X7hx9nHrCXmV9YQsQyer2JglSVp3C1yJPlInEiSosKe31oNdOcM2PHYkm9gtlQQCoku/0RlK9ADbkfePZf8D6IW8x3gXULpkDePVaNcqpJwEg8oGlxCNGZa0jSXHTeclCDugcsPaHvy1QF3PFN/lGKjScRg74WfOywJLS64cpr7VaBxo1jDPdNau/ld6wyyrrDSLcvwKMY6EWVYGZ/0I49KzymERS8QANr6GRY3kTXwEMuJehNOZkoradVimZb5k2SVnFy7qZCbRLTjMI3NnfU7EXXGgl12XCvDu6cYvd+Lkz6SWHqSr7+Utkb1msePUM0dtsi+ArWIJ/0aJqghu7Pja+pgKYJtp5kZGq8QIUZYwT6KrXYbxnruFzuUAgKYSgA01LGsC4nKX7m4A7vQNg/np+kZP1tTibNa5DGrEM+VtZmGgGLjmeRhcCtcHlaNG4RpQQJFpxhO7ooXjQyVLaHjztDdpWbfRfjUEeao1WnnhlqjjAXc70aTbPwMAdkfzqVxb01lbAySVMCH/knUkUNMJ08wPKejKfKeiSeGbhhFuGdHDrLhfN1vFp+W1XvxXo0obWpecIxaJgN/46Uq6qhsSLrkh54yuafrVU4+DEp8a2d6NPZ8MBsDdfSeLqzlWoWdY34SSQ++jLxY+AMYOYK1675IwG+0xp2yo89gLkrl0fF5ei2+w4kDDpbx6rV6HnOpaaMD4rCb0F3+QyCAEqasb3DakDm+EHSd3TFsVViXNpLNaCf7DzoOkgOOPArVAYuiufwYoEcroyMbtHO0sMSNe1l/OHwx9iWiHcKywZ74OMLVxQYCeuBiwUDdnW69r1//DEdv/7ehtFWVUtBL39Yf/Ng6M1AnTCD+Ub3yGW9FjSO3p0W250zpcCgpER0srQulu66kQiwAClCKXklt9TzPukRdBRIwDXUO8OYoZ/KZCPy1aZG90DQBHCMajQicDRG4e6j+DmpkinDMsn3sv8evy2CSdmoYZw5fuN+oUU/TQFQFLflG9hg11o0hmtq81HmC+w7ERPeWHCC1r4qdhxHLdGKOM19XXJXMxZw3FIsT6jr2+HhhBr0LImYlijSM1tLwnVqsKBdc6uDIvFGSMHCXc0GF2pVhxW4APJZP+vCyd/KQ4IUTXPZsu+HtNaTUY3EIn+6KsT3suTCX3QoWN0EDkFVTDN5sxov9rVPmKW0P8zEJIBbcxK2eS4zKRjL6PjlF5vWPPLPLY2INQ59gh20eFsu1QyIh2au7dKTyhbh6FQ6xGmsssIx8XzhnfvOIv/LKEEWBHVx2VWsqskdATP/Vq/xbpmWiLpxrvT32vyUyv1X7T6YKpIancFUoByNGVZKjYepNTPeF8CIh4dTNTt68QhLzgHzeHZAF62p8lMIFA2asDzqsWfX091JnRVJnZ3bCrOA+GojqT2Ao91teH6eGCaifzlAb1S3GmnokyZhziaFb9L9h8QHxZJ7wf1s9wOP4WxoGuQ1aZVoV6vORSlg6qrfLPlAr5/799Y9GdiOc/2QAlpUYw7D0q7B2qCDh0R3KUIsvvQ+Nv8YnfQZ/og3+SnOwxipaMcWYOimLhTb4bAyNfvmzh2rPER9kY9mOh"}}]}, sign=3E11A47A26901129A968A57C0DF6F6A54483A300732DBAB8C89D9E57E345547B, return_msg=OK, result_code=SUCCESS, mch_id=1513549201, return_code=SUCCESS}
                //2019-11-18 调接口返回=>
                //获取平台证书响应 ={nonce_str=DeMtFrTc7Rhn4uH4,
                // certificates={"data":[{"serial_no":"749535475D89DF9ECF413A6CA05F74FF4CEDDCB8",
                // "effective_time":"2019-05-06 16:45:20","expire_time":"2024-05-04 16:45:20",
                // "encrypt_certificate":{
                // "algorithm":"AEAD_AES_256_GCM",
                // "nonce":"817fe64c47df",
                // "associated_data":"certificate",
                // "ciphertext":"dibaLlCMiIP2g3Yq8KQLXyKXjODyll9JhTkpeBz6A6u7KxxGvJjVL2HR8gyeiS9Wz2l+FfXNgJ7MPbrSYB/FUrPkRQLuZXo5SXZa+5lGSO5+XcUYTja8C/Q/GWXbvA/y0AxrpP7DRV/2wXhD+VSv1V9QhVzqF2d/WijKlGPnEyxcIb08V/Og2/H7CZYjTIT3xWA0WMfNtN37/k2eW7fY2R3B6SBSz9zwigVUfyYgQe8fNQn7QvN+ZKUJqJYE9rcvddYeKQI73FhoarU6brQLldtEm0JIh9qHGBss/19PwqGJ8QApP6+g1hDts35WSsJ64fY8S3JZ3Q7qkHqJKB0TX370LGrD/cbt4JxK90OAQt/d3nz+6BpEF1FkODcjJeo6Eauyu8tDXGJBy5byQPjIBFllgukV+nEKLZZeMADxWwxisE5HkJzq7wnz2A+33oCHH1kLpsjPYlMBy/QadlEjTwlox9GyEn5eLXMEuvLnBjo+qRF42g9SPAHgrKld88B/4tiqjhbQhsHyYigoli0JFWGNBIPN3L0Vt5LsoUV3y7j6reIpx1AmuqWdk5Yv59BjIAcgeL/bIeFBOWjBi21400rWeAgZ7mp6VOBwA4HyxGPYZPB0mATHVBsLU0sNHLXwPFsSeGVv/IEhMZyGFEhtdU+HpRltpw3ih0DU2A+J+nZMqxRerTp39lzn4tzXaRXCqQMwyDY0ln4ABlH4ZGObbNhq/ZCpXhdB+VVMRbaVDqeYKL3eDN7OvVk9Ac0Tv6sIRaskM3S4lSv8apUaV6VNAdE7Jq1YpGDoQvcNoBDJ2lig1fNIp464B99wVoykesTYv2CIyx25nYf5+qS1jbCcHafp9hVwimBabdHUOMiYJV4lRqN2eeakRp+EW7ChDBeP+nISEVnaWUxfhmMeTduTf/IQEKQlgT7NoxNFcaFg/VL3DHEw4KNP8J3xfGUDtWIowCITxR55ySAc32Vq5jrrWfdA/Z1NpjuJrUbOfuZkwvBUxYne5xpqS/kXvdrJMXclsY3LU5wXyoPAahUKHuXVOqhilGxm8N2XEhzCG7cCa2CjhxgEuRdCAZP6rZ6wl8GChgD/2TjF3HRJzNQgHJv5e6izSWkg1b7Ug0TTzbb4zxW185Y5Oe2/m0v81H09LyV8Zj8HOx4QjvZFS2ORHqIFtlqi2+AQHYT4k1DwRfH9+9aqmTXnHztYMbUL5hBMzIM6oGtIfJqggAFbVOK+eTyGK+wQn16VBO/l+5n0E7a3zL3tDL7eKemma4t0qDvkSRaBx/J4pE9+1vBEvY1ARsVaQnjXRmRHIatyCKetZxpJymtXAELH2Gv8QckiduIaXL2A4Yy++WFfpk5ey/yOZguEWFGgkLoomyK9+yDeggLxzVJLJJIKQIkRz3AW5BImjfMOi7hTJIaVzFmwL5h99smC52/0XxSMhE5QOQFfu2/w8jG3jbNF+NgrcZoPyijJFUmKPJES+0+069+B3F8DN+MIIDLUITP7VCYvggcPLeEzIk4M3l9TvzZDOK2L+yfMhHtXelfrQlxy3AY22IWu10DyaZqykPp4G7vJe2TG4Vwt8rmgqPU73yvPux9DVHC6acr08Ht8M4JlJpneof9l7llxE0W13AqfbZOgk6pBr6cdDtFoy8ThkLIhRosetC5IrhsAAxjnkfrmxEFYQshHxlMML2deM4n109aBE85MVy96lXqdffyYhbH1NbqRXPOzvCLu3mQ7rdtQk3c2AtLJc0ZwlExVc8l6tpp1pXgfPuzNZC7+ICFy+S9lu29x1Ci6Bi+/ETAvwqXQF081mYpq/+e4XAkjZOH/GMfFcLdfNCHkENd/SzPGKJX0uXmZphe8u6Y4j/saNtAZBPzb/IlqkrDMatw/21IPu3OytfmFE/OALrpym7zis0+ByC4ieLNZtBPDexr0"}}]}, sign=A00FA89D07DB91C243FDCB185775143DB2CB900A59EBA613603265DAC86B8EC2, return_msg=OK, result_code=SUCCESS, mch_id=1513549201, return_code=SUCCESS}
                if (m != null
                        && m.get("return_code") != null
                        && "SUCCESS".equals(m.get("return_code"))
                        && m.get("result_code") != null
                        && "SUCCESS".equals(m.get("result_code"))
                ) {
                    return m.get("certificates").toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            log.error("执行httpclient请求平台证书序号错误 {}", e);
        }
        return null;
    }

    //平台证书解密出pem明文
    public final static String decryptCertSN(String associatedData, String nonce, String cipherText, String apiv3Key) throws Exception {
        final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(apiv3Key.getBytes(), "AES");
        GCMParameterSpec spec = new GCMParameterSpec(128, nonce.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        cipher.updateAAD(associatedData.getBytes());
        return new String(cipher.doFinal(Base64.decode(cipherText)));
    }

    public static void main(String[] args) throws Exception {
        String associated_data = "certificate";
        String nonce = "3b5dde2e50e5";
        String ciphertext = "A461HRBW/lfvLrb4og2Z2XvVkooykV3n6yNZHk2X87ALFweH5m20DTTH0uRJEwJW5F7b7yKVEW2rIejXoKUkK42koIRtxdksLgKmgFwXXQcpyZdLO5fTbnqnkIHtcItliJlCvJILVZlOAltA6oDh23c/w82lYjT1/GJZBJKwD9mfr1WoKIntvrERjjaMQHQpSn1btum5CaQjWmTnWKYB7Wx99mVIR0zoQrg/Y0FhXu0XV97Z8BuR3qmfKutR9agykvrpGw11S+y+AVadGo4Dm+X4zeQGkqg/zSHPj3wO5sIb3GqERbNF2zi59l93Y9USfb4wTEGdrZ/hf7PabO0dkR9LTT4DWBknJomKWh+k+p9PJUYLn8K5jn0O3nFI6W2/l5ALHE7VIZ73DmRDz8l0odOq12UUzcP8SHMtMiUMlE1V7Oe8vDUM+cgCesOheR0ciHPmdD526Dk6EkEaDXwteu6lqrZHk4qAnG8l5AsNgQxBVzwQfxzoIAyh7GlqfATYYYftoYhIIY9cJVTEdbEU0q+L7UvX6HM4WFPyEJR569HGmJXCJ/BvK7YiFhqso55Qzpv68nSlOXQI5SHi4MYLGMxh1fjOQOhKO1AJLiOYKSMucqo+RmX/55+j7dVLEbPAhmHV98DD1jttBsfBlU14Ari2E1Scho6g2Sh08p/nXve/zQ7sz819O9dsOApSwsV/gEphI1CnMcnpxT2dleBbyDTATPJ3kLdcdzzh8gZDdonRvTtXXaiaNkKwlf+ZQhtKAcHGe5fXqtZ+NPs9Juy2sdhqgMk9SRVn9Ucs7/wgxn/SVbXurc8ZxdALd7WFIPuXI0cu+xzjAU+x98hn2V+cUJImFGDXr2KsiDtlz00rL+6WM0yuAb0rwjZ8RTt5FT7zlp6f2LGMLrCbMxR0TQ1lr8nMSUCM3JezWLNuShKhB5eMlwmM9EGkk+ozc1zEz/9oXZEnoHhhj5yUVnHNvEgfspQzdVq4/YOod5tTUGmWjolwQgD4JQOcy3GYTvlXf7bZP705o7FhB7Q61a6v3EuErWQW0m8NwxMs1i9XUhzMMlwcskPbq8/udvz7fHZgtU9wpmUg/FULM80omJHiEI/tnW4bHp2rjd9iKYC6cgxT0vG+/HEl3lkjZwkZJBrN0gQrKTfzAFwIN33kDEbxJ9XIBaKp77agVUkg6jY9EpJb+fzuXUC6IKeaiblhwLI+9eZba10xmNFTGldIKMa8hHMVKEhqXFgdGjNFGSRkuGDBTtUegDcj9wydBABpNsYr+DBuxkThq6kC/+IZiqTxojcWpZy1qdWjqnTVc3iWwOUdbthjk4PqEyH7b95Hz6YCCmqmZlBWbYWFV0b219FrT/PfEJlaVPm0m9Dz2zamqO5cE1xaZ+3gCwq8AAWqq8+Bl2P3FV/5iFyWlxS1QIBCwS0Xo50GGdGHZe/iMm36AzOts7SrGllMoX7Z0VJSuevOj6kc9LTeynx3bAfsORK4G+9PIrxhUT7/KoLvNAsur9lxZ7IK+p2MP6ZSMmPhk5biToDYR+nqVHKPqof6FPU9wWWpns2h8N9lZBFjZyfUfLHFFZbxx1XDTe/R3v+c7THuL2oleI5lek5Hr0rvX1G6vV5lx4vWHb8nJVq7WB0QyXoi9qsn225/2U7oqIdpEMsZ8O3bX+GpDQa3ckyvQHyf0CapJO0AueHoi3R3Zko/7L56jCovmN4FQROFqAHtxiCdEuzfzufwTKzR5XoGG4yBny4rB3Gfn7AgBFy9eTojUHkFjCU7ZE4fitRaXtEdXNwuAM6fj23zrZUQnauMs97HWhqFuwrGaw41C559jVgiJIcvPwwejC713MBNFZAKxGglolzpjuEctZj2ZK+vabBw3RNR9yfp/WJH45GXtiEH5eSZidNxnet7AeyOao3+YzeKXjy6DKJ2";
        String apiv3Key = "b649bd1448add18b23ac1ee1fc960f8f";
        String content = AesGcmUtil.decryptCertSN(associated_data, nonce, ciphertext, apiv3Key);
        System.out.println("result=>" + content);
//        String encrypt = RSAUtil.rsaEncryptByCert("我的身份证", content);
//        System.out.println("result=>" + encrypt);
    }

}