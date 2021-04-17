package com.otzg.wxpay.data;

/**
 * 微信h5收款对象
 */
public class WxH5PayData extends WxPayData {
    String spbill_create_ip;
    /**
     * 描述
     * 该字段用于上报支付的场景信息,针对H5支付有以下三种场景,请根据对应场景上报,H5支付不建议在APP端使用，针对场景1，2请接入APP支付，不然可能会出现兼容性问题
     *
     * 1，IOS移动应用
     * {"h5_info": //h5支付固定传"h5_info"
     *     {"type": "",  //场景类型
     *      "app_name": "",  //应用名
     *      "bundle_id": ""  //bundle_id
     *      }
     * }
     *
     * 2，安卓移动应用
     * {"h5_info": //h5支付固定传"h5_info"
     *     {"type": "",  //场景类型
     *      "app_name": "",  //应用名
     *      "package_name": ""  //包名
     *      }
     * }
     *
     * 3，WAP网站应用
     * {"h5_info": //h5支付固定传"h5_info"
     *    {"type": "",  //场景类型
     *     "wap_url": "",//WAP网站URL地址
     *     "wap_name": ""  //WAP 网站名
     *     }
     * }
     *
     * 示例：
     * IOS移动应用  {"h5_info": {"type":"IOS","app_name": "王者荣耀","bundle_id": "com.tencent.wzryIOS"}}
     *      安卓移动应用 {"h5_info": {"type":"Android","app_name": "王者荣耀","package_name": "com.tencent.tmgp.sgame"}}
     *      WAP网站应用  {"h5_info": {"type":"Wap","wap_url": "https://pay.qq.com","wap_name": "腾讯充值"}}
     */
    String sceneInfo;

    public WxH5PayData(String subMchId, String spbill_create_ip, String sceneInfo, String body, String attach, String outTradeNo, Double totalFee) {
        this.subMchId = subMchId;
        this.spbill_create_ip = spbill_create_ip;
        this.sceneInfo = sceneInfo;
        this.body = body;
        this.attach = attach;
        this.outTradeNo = outTradeNo;
        this.totalFee = totalFee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getSceneInfo() {
        return sceneInfo;
    }

    public void setSceneInfo(String sceneInfo) {
        this.sceneInfo = sceneInfo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }
    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }
}
