package com.otzg.wxpay.util.service;

import com.otzg.wxpay.dto.WxMicroAccountDto;
import com.otzg.wxpay.entity.WxMicroAccount;
import com.otzg.wxpay.util.AesGcmUtil;
import com.otzg.wxpay.util.RSAUtil;
import com.otzg.wxpay.util.WxMicroAccountCheckCheckUtil;
import org.junit.Test;

import java.io.File;
import java.util.Map;

/**
 * @Author G.
 * @Date 2019/11/27 0027 上午 10:29
 */
public class TestUtil {

    //=====================================小微商户===========================================

    /**
     * 下载证书
     */
    @Test
    public void downLoadCert() {
        System.out.println(AesGcmUtil.getCertFicates());
        //{nonce_str=pPzUa2GqaoPPMEcu,
        // certificates={"data":[{"serial_no":"749535475D89DF9ECF413A6CA05F74FF4CEDDCB8","effective_time":"2019-05-06 16:45:20",
        // "expire_time":"2024-05-04 16:45:20","encrypt_certificate":
        // {"algorithm":"AEAD_AES_256_GCM","nonce":"e051d7cd6865","associated_data":"certificate",
        // "ciphertext":"SueHCzpMeXi9/1laYE33EyHlDDHbOzUnqqdcz7joXdRY44MYBz7jQCB3elnBhcWq0e5hoMZZ2q4B7o21j09miCO16jcDlIq/6Wbm/13Jf5zE482nHnPUtnULmhxHmY6Id8T+04+nIuoE+G2d5GswaNXP8/iPmgYkn3Bf4icyIeTSYYu9vwdAZIxWu6ONE3Tb9ZR6ee0zHfU/MfGFgoomWgHIii+6hTM6BZVkjPiyGFTu4yFP/gYs+Fl+AEdgLks2gBOQEGLk6mU/w1o3VAjIVG1b++ppoon8XPfik5l8pv3Rew4CsC7d28O+lw6f5jB3IQ2kMqQ9RmCMzzcM40smJ8A38AEkp3tE2vPeDfFVRR5UsEidmiJOmjD8noYfiYW+0jRRJRYz1xWVbRdtyEyP25CNd+VdFnpFlMwN2vmgpU1dJjJIv3u+hxYXiXzM2wRtcbgauWxNAy4bameyVf+tnpzZDVqtAQg3TXDQ1gB5/W/+lXoF1eBuZNPRV9Lal7H9Sfi74ne9PpZ2ufxXBvZIZ1lx7q6s2zB++ZXAdWJQ408Nzgxs8+wa1m9lOjhl+itRguA/wIL4UN/1mmKFdlDifEXef+7ZS6tfbSnRUKB9qxlgozhquqGZRImuCJMXRxDiMTWN8yCb9zSGeIxSJ0ZueqXNMjKhcZcZVoJQf2xkfKaGzbq69+0bYW8+cyIjaapawzCp8Dv6yFHYATdBkEu5iRZN5Itz116u0dZtCigqfS7PVKVmjBJEGHgchXCaukTOhHG24I+gJHJC4uhi08cKjPVYA82YWfwaASO7r8Bt0ahbPV6tD763VLpovp3dBSP7YIqayiAAocsMuSlkBeBYTztlsoC3kj/5BVHe9fiY7j7NXwAvO+ilDraAbSD0p9GYFKTcbCDG3PClMDAWqTjX6kvszlGXPlsKN5AicAooND2ddxIIKduBpgxICCvYApYgHMpebA+Q+0uCfXiUS6kF3DJS6C6EVq6kSLsGGRlIJQ7PgRDUGRQq95CEem90Z913p5n+eCgyqJ0c3tGmyQ6k2aeEZrjBvWXu+zKiJ+5ZBuc7IleAF4EtVv+xnvKMBFuQSWtlHJPe1utOhCkyRdTIpB01WjMM76SnwlRPqWz0aiVOg1a8Yoq3VYjjD466/fhPg4bHoa5zI5xBxnXZu2uEn50gyqsfMSel3GWW+cbj8CL2YBn8rPh3VB9fvQ+IBXkZgXDwV8HSxM46QVUs/PNJK4y1LSRC7f5HTPIeNLzpJsLyleGCsZRnklwGMOhA/By7YYwL3/Fp/+1SaqTGX+/cYNGtGKBcCKl4uVFw2QCChmK5KX98AfniBLK+SuvAQybnfQKCvQgtJWQWoPReBQkjEnRziftKxpHJ9mFIz2Xg62QKY+uZ2JsjV8sJy/2ibsCa6LDVup73U7PaD6VEhvW8zwuHw0NWwr5QFdAVQKnlbQx11keJnvwl7tmvPFtA+vfdNosW4GxgfTG8fYY14kxf71YQDBCVUgTOyWBkdOoTItiHHjeofRrNjf+Qq5/OMrRYPJPhEDu+DDquZjGdVykjV4FhXbfqY9xDGpWvO50bbYo9tnXFYp6I4NCkD8vNRzGF4+171CzjMSfvAX1SjeOAlR93sG0frQNBnzLk0tgpXgSETyQBIQVICky0bdTzMGmqv07T/WSFZ0xOXxahcNMKUDdzjksmQURY3A2knKeOIdl4Ssbc5hQi/yZoygrlGBuW/BcuYXaERyvcfet7ITK1ksu5NmbE4Gdz1k4zXqDa3iVYF+2o/4Rrg+isuN1NBq4HnFR1W8RqBMgJLPpDbQSBtJ6LVDeEiBKiyyJ8kZjMZaREMpRoFv0WoNmMIF6no2Yr905Tr+8+LqMV4VUMU6MxRHGd+/zmw+wvwGNKmPud0NeABVm6w0hC8Oo8Qy7VYlW50+cJ"}}]}, sign=926F9F829DEE433D6A7638698625EB3C1CC4DBEC4B846BCEF65C69C623C44CF4, return_msg=OK, result_code=SUCCESS, mch_id=1513549201, return_code=SUCCESS}
    }

    /**
     * 证书解密出公钥
     *
     * @throws Exception
     */
    @Test
    public void getCert() throws Exception {
        String associated_data = "certificate";
        String nonce = "e051d7cd6865";
        String ciphertext = "SueHCzpMeXi9/1laYE33EyHlDDHbOzUnqqdcz7joXdRY44MYBz7jQCB3elnBhcWq0e5hoMZZ2q4B7o21j09miCO16jcDlIq/6Wbm/13Jf5zE482nHnPUtnULmhxHmY6Id8T+04+nIuoE+G2d5GswaNXP8/iPmgYkn3Bf4icyIeTSYYu9vwdAZIxWu6ONE3Tb9ZR6ee0zHfU/MfGFgoomWgHIii+6hTM6BZVkjPiyGFTu4yFP/gYs+Fl+AEdgLks2gBOQEGLk6mU/w1o3VAjIVG1b++ppoon8XPfik5l8pv3Rew4CsC7d28O+lw6f5jB3IQ2kMqQ9RmCMzzcM40smJ8A38AEkp3tE2vPeDfFVRR5UsEidmiJOmjD8noYfiYW+0jRRJRYz1xWVbRdtyEyP25CNd+VdFnpFlMwN2vmgpU1dJjJIv3u+hxYXiXzM2wRtcbgauWxNAy4bameyVf+tnpzZDVqtAQg3TXDQ1gB5/W/+lXoF1eBuZNPRV9Lal7H9Sfi74ne9PpZ2ufxXBvZIZ1lx7q6s2zB++ZXAdWJQ408Nzgxs8+wa1m9lOjhl+itRguA/wIL4UN/1mmKFdlDifEXef+7ZS6tfbSnRUKB9qxlgozhquqGZRImuCJMXRxDiMTWN8yCb9zSGeIxSJ0ZueqXNMjKhcZcZVoJQf2xkfKaGzbq69+0bYW8+cyIjaapawzCp8Dv6yFHYATdBkEu5iRZN5Itz116u0dZtCigqfS7PVKVmjBJEGHgchXCaukTOhHG24I+gJHJC4uhi08cKjPVYA82YWfwaASO7r8Bt0ahbPV6tD763VLpovp3dBSP7YIqayiAAocsMuSlkBeBYTztlsoC3kj/5BVHe9fiY7j7NXwAvO+ilDraAbSD0p9GYFKTcbCDG3PClMDAWqTjX6kvszlGXPlsKN5AicAooND2ddxIIKduBpgxICCvYApYgHMpebA+Q+0uCfXiUS6kF3DJS6C6EVq6kSLsGGRlIJQ7PgRDUGRQq95CEem90Z913p5n+eCgyqJ0c3tGmyQ6k2aeEZrjBvWXu+zKiJ+5ZBuc7IleAF4EtVv+xnvKMBFuQSWtlHJPe1utOhCkyRdTIpB01WjMM76SnwlRPqWz0aiVOg1a8Yoq3VYjjD466/fhPg4bHoa5zI5xBxnXZu2uEn50gyqsfMSel3GWW+cbj8CL2YBn8rPh3VB9fvQ+IBXkZgXDwV8HSxM46QVUs/PNJK4y1LSRC7f5HTPIeNLzpJsLyleGCsZRnklwGMOhA/By7YYwL3/Fp/+1SaqTGX+/cYNGtGKBcCKl4uVFw2QCChmK5KX98AfniBLK+SuvAQybnfQKCvQgtJWQWoPReBQkjEnRziftKxpHJ9mFIz2Xg62QKY+uZ2JsjV8sJy/2ibsCa6LDVup73U7PaD6VEhvW8zwuHw0NWwr5QFdAVQKnlbQx11keJnvwl7tmvPFtA+vfdNosW4GxgfTG8fYY14kxf71YQDBCVUgTOyWBkdOoTItiHHjeofRrNjf+Qq5/OMrRYPJPhEDu+DDquZjGdVykjV4FhXbfqY9xDGpWvO50bbYo9tnXFYp6I4NCkD8vNRzGF4+171CzjMSfvAX1SjeOAlR93sG0frQNBnzLk0tgpXgSETyQBIQVICky0bdTzMGmqv07T/WSFZ0xOXxahcNMKUDdzjksmQURY3A2knKeOIdl4Ssbc5hQi/yZoygrlGBuW/BcuYXaERyvcfet7ITK1ksu5NmbE4Gdz1k4zXqDa3iVYF+2o/4Rrg+isuN1NBq4HnFR1W8RqBMgJLPpDbQSBtJ6LVDeEiBKiyyJ8kZjMZaREMpRoFv0WoNmMIF6no2Yr905Tr+8+LqMV4VUMU6MxRHGd+/zmw+wvwGNKmPud0NeABVm6w0hC8Oo8Qy7VYlW50+cJ";
//        String apiv3Key = "Bcb19e0814494a248d28c591d4bc31e1";
        String apiv3Key = "b649bd1448add18b23ac1ee1fc960f8f";
        String content = AesGcmUtil.decryptCertSN(associated_data, nonce, ciphertext, apiv3Key);
        System.out.println("result=>" + content);
        String encrypt = RSAUtil.rsaEncryptByCert("我的身份证", content);
        System.out.println("result=>" + encrypt);

        //result=>-----BEGIN CERTIFICATE-----
        //MIID8TCCAtmgAwIBAgIUdJU1R12J357PQTpsoF90/0zt3LgwDQYJKoZIhvcNAQEL
        //BQAwXjELMAkGA1UEBhMCQ04xEzARBgNVBAoTClRlbnBheS5jb20xHTAbBgNVBAsT
        //FFRlbnBheS5jb20gQ0EgQ2VudGVyMRswGQYDVQQDExJUZW5wYXkuY29tIFJvb3Qg
        //Q0EwHhcNMTkwNTA2MDg0NTIwWhcNMjQwNTA0MDg0NTIwWjCBgjEYMBYGA1UEAwwP
        //VGVucGF5LmNvbSBzaWduMRMwEQYDVQQKDApUZW5wYXkuY29tMR0wGwYDVQQLDBRU
        //ZW5wYXkuY29tIENBIENlbnRlcjELMAkGA1UEBgwCQ04xEjAQBgNVBAgMCUd1YW5n
        //RG9uZzERMA8GA1UEBwwIU2hlblpoZW4wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAw
        //ggEKAoIBAQC11UJ8ksl+K53jwj1Zwn4shOtf5yCtCe/BYUMUrKytVhkk63JoLSGW
        //65O1pfjANw1zwNsLlnHABbdTQMm222rynB7sZ2y2ruj858p1WJIf4gfhS70mBdDr
        //niR6nCRBEsFmY0i08qsNAJT4L6zQZJH9CHkm6uzQNB8OkNumNC7eyBhqFv4BkHLr
        //s6PHj+S9FSzNxgjKCVLQOF+ygn8aKwUQq//i4b+1K27CWBkTNqeg+qE9BCzn4ZUB
        //m7aKV96YDUnf+zrVyVfH7E/vphld+Mg2yGyVSvmb/hCh1VuAEWUzF/qOSKFOWNDG
        //4GHeplzOrtS7une6b4aYOtdiFGZwzq2JAgMBAAGjgYEwfzAJBgNVHRMEAjAAMAsG
        //A1UdDwQEAwIE8DBlBgNVHR8EXjBcMFqgWKBWhlRodHRwOi8vZXZjYS5pdHJ1cy5j
        //b20uY24vcHVibGljL2l0cnVzY3JsP0NBPTFCRDQyMjBFNTBEQkMwNEIwNkFEMzk3
        //NTQ5ODQ2QzAxQzNFOEVCRDIwDQYJKoZIhvcNAQELBQADggEBAJC+SSHxDqc4F3Ox
        //i3bgSHz+FktlDWSfSqewfxqxCI6ViyHyEe0DkdeR3tD85GK8KDR9HcHvgh+EV3z4
        //rj9Ex+m0MYfcJVgpd1UqeORLHZkjBYQqdiKZo4eDRGB4ekGbFfUZH/n5agmY/1Kl
        //kQyec1SdQyOj1SK0N17k75I7l0zb3sc49VEREKAyTX5b2RLdz109DP/4DjinINhy
        //Lnl6Clw3JrPEUCERKRpcf1St2tlJXHQqfggy9KJmkJeZPuZiqEP4auI1C4mFCvka
        //CXnJ+VcMBAydl6uIhe5nxzltYXFlDN+2vF0zrJ7WSEQMPp0qF4uD9lKCoY9AvYTu
        //b1qyLv0=
        //-----END CERTIFICATE-----
        //result=>kS5QthoMMvuNKAJ6V/2YEs2M/P2o46EMCLuC6YLiV4xN11MdKUw5TCRtiI7ydMMDLeuZpxve8udBybAI6KHTuYSJqSVOIo6oqwrfrynXVNNc3wyrHB7fvlbT2LNmYeCe9zMhhAeLpNE+mlnxTJD12qumh5Ph5LQVfCsimzgQolCc83NCeWmzBMpAMveUmV3gXfHg280ji9r15coU8tl7JiTbGyWcGtiW0STcM8bbMq5GEpXIG+7x4hnucUEyV3sw8LQnaXWHm3oxR2lVDpuX3qfGK4buIk6vgIrC2deEwV5Qutnwou9P8YJ5M9NVsh0ujFBJPDMsNZFXzfzk9El9DA==
    }

    @Test
    public void testMicroUpload() throws Exception {
        String filePath = "C:/Users/Administrator/Desktop/商品信息2/0df05c892726c1cac5062b01fd03fb1.jpg";
        File file = new File(filePath);
        String result = new WxMicroUtil().doUploadImg(file);
        System.out.println("result=" + result);
        //J8pnFETqjoeff9HpkJJtD9wsedcd4dHmMfXS-7qzBuyH3OLCaal33ZKvl4LIqDlwdNfkJfts3J99dsRS3FigbqTBBJfgNfpw7myCkc0xrpg
        //g4p1-S3dOEfQa7dsIGhy5tONDLJiwB0EZPTcmZiPPwn9lnWcwiOoFYd2DvZbzPoIU1kwvbQTXUkOXyFKSHRqS7tuPPhYnF9PtEWSaKoBzMA
    }

    @Test
    public void testMicroSubmitQuery() throws Exception {
//        String applyment_id = "2000002132952816";
        String applyment_id = "2000002132954441";
        String orderNo = "1132135";
        Map result = new WxMicroUtil().doMicroSubmitQuery(orderNo);
        System.out.println("result=" + result);
        //2019-11-29 11:45
        //{nonce_str=aSrxOyllT19SBaIf, applyment_state=REJECTED, sign=04765E3E7D04DE30200AFF8F81729EEDA036E7C8F8C0B9BBD608039DA2A90DC4, applyment_state_desc=已驳回, return_msg=OK, result_code=SUCCESS, audit_detail={"audit_detail":[{"param_name":"id_card_copy","reject_reason":"身份证正面识别失败，请重新上传"}]}, return_code=SUCCESS, applyment_id=2000002132952816}
        //result={nonce_str=aSrxOyllT19SBaIf, applyment_state=REJECTED, sign=04765E3E7D04DE30200AFF8F81729EEDA036E7C8F8C0B9BBD608039DA2A90DC4, applyment_state_desc=已驳回, return_msg=OK, result_code=SUCCESS, audit_detail={"audit_detail":[{"param_name":"id_card_copy","reject_reason":"身份证正面识别失败，请重新上传"}]}, return_code=SUCCESS, applyment_id=2000002132952816}
        //{applyment_id=2000002132954441, mch_id=1513549201, nonce_str=C43ED6BD0BC2D9C7, sign=1DAA638B55252E54184790A22579F1D2AE73D8D01B095FE4422A60EF5FBFF10F, sign_type=HMAC-SHA256, version=1.0}
    }

    @Test
    public void testMicroSubmit1() {
        String orderNo = "1132135";
        WxMicroAccountDto wxMicroAccountDto = new WxMicroAccountDto();
        wxMicroAccountDto.setAccountBank("交通银行");
        wxMicroAccountDto.setAccountName("王志刚");
        wxMicroAccountDto.setAccountNumber("6222600620015541041");
        wxMicroAccountDto.setBankAddressCode("410102");
        wxMicroAccountDto.setContact("王志刚");
        wxMicroAccountDto.setContactPhone("13703957387");
        wxMicroAccountDto.setContactEmail("375214167@qq.com");
        wxMicroAccountDto.setIdCardCopySrc("_qdPgxYeHjr20zZTSLO0ytytIW4WzQGYSjL9keglclA2AQyG_RoQhc87zbvCHC6B_zgcfMm7mi2zlOPEErgzFfy8z");
        wxMicroAccountDto.setIdCardName("王志刚");
        wxMicroAccountDto.setIdCardNumber("320106496903060011");
        wxMicroAccountDto.setIdCardNationalSrc("W56fsKod1rgFm3W7Xin0HIWYTvWcHWNN2l62P8myGhDeN7dwQ3Xzen1K3EdCEuzRIOHUpEky61GQ8lZQ3JjxDrZ_k23PUCJc9pQathUIR8I");
        wxMicroAccountDto.setIdCardValidTime("[\"1970-01-01\",\"长期\"]");
        wxMicroAccountDto.setIndoorPicSrc("W56fsKod1rgFm3W7Xin0HIWYTvWcHWNN2l62P8myGhDeN7dwQ3Xzen1K3EdCEuzRIOHUpEky61GQ8lZQ3JjxDrZ_k23PUCJc9pQathUIR8I");
        wxMicroAccountDto.setMerchantShortName("天天小吃");
        wxMicroAccountDto.setProductDesc("餐饮");
        wxMicroAccountDto.setRate("0.6%");
        wxMicroAccountDto.setServicePhone("13703957387");
        wxMicroAccountDto.setStoreAddressCode("110000");
        wxMicroAccountDto.setStoreEntrancePicSrc("W56fsKod1rgFm3W7Xin0HIWYTvWcHWNN2l62P8myGhDeN7dwQ3Xzen1K3EdCEuzRIOHUpEky61GQ8lZQ3JjxDrZ_k23PUCJc9pQathUIR8I");
        wxMicroAccountDto.setStoreName("郑州天天小吃");
        wxMicroAccountDto.setStoreStreet("郑州市工人路100号");

        //校验
        WxMicroAccountCheckCheckUtil check = new WxMicroAccountCheckCheckUtil(wxMicroAccountDto);
        if (!check.hasErrors()) {
            System.out.println("check.msg=" + check.getMsg());
        }

        WxMicroAccount wxMicroAccount = check.getPojo();
        wxMicroAccount.setBusinessCode(orderNo);


//        //准备数据
        String applyment_id = new WxMicroUtil().doMicroSubmit(wxMicroAccount);
        System.out.println("data=" + applyment_id);

        //{nonce_str=BHcQlY4ygaQO7QZk, sign=84EDDB8F4A6B0B1350CFD2EAACFB49E679E5B50791F1A22375656A6138665953,
        // return_msg=OK, result_code=SUCCESS, return_code=SUCCESS, applyment_id=2000002132952816}

    }

}
