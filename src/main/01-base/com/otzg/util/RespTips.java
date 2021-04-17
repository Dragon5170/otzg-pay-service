package com.otzg.util;

import com.otzg.enums.CertificateType;

/**
 * 操作结果提示
 * e.g.
 *      RespTips.SUCCESS_CODE.code 提示代码(机读)
 *      RespTips.SUCCESS_CODE.tips 提示信息(人读)
 * @author G/2018/6/11 14:37
 */
public enum RespTips {
    SUCCESS_CODE("0000","操作成功"),
    ERROR_CODE("0001","操作失败，请重试"),
    PARAM_ERROR("0002","参数错误，请核对参数"),
    TOKEN_ERROR("0003","Token校验失败"),
    WRITEOFF_ERROR("0004","只能核销本店铺的卡劵"),

    SYS_ERR_MSG("0005","服务端异常，请联系管理员或稍后重试"),
    SERVER_IS_BUSY("server_is_busy","服务器繁忙,请稍后再试"),

    PARAM_NULL_ERROR("0006","参数为空，请检查传入参数值"),
    PARAM_SQL_ERROR("0007","数据操作有误，请联系管理员或稍后重试"),
    PARAM_LENGHT_ERROR("0008","参数长度有误，请检查传入参数值"),
    PARAM_REG_ERROR("0009","参数校验失败，请检查传入参数值"),
    NO_AUTH("0010","该客户没有权限，请分配权限或联系管理员解决"),
    DATABASE_ERROR("0011","数据库异常"),
    UNKNOW_ERROR("0012","未知错误"),
    DATA_NULL("0013","没有数据"),
    DATA_FOUND("0014","数据已存在"),
    DATA_CANT_DELETE("0015","数据有关联不能删除"),
    DATA_CANT_OPTION("0016","数据暂时不能操作"),

    LOGINED("9000","登录成功"),
    NO_LOGIN("9001","客户未登录，请登录！"),
    UORP_ERROR("9002","账号或密码错误"),

    USER_FORBIDDEN("9003","客户已被禁用"),
    NO_USER("9004","账号未注册"),
    USER_REGISTERED("9005","账号正在审核"),
    USER_AVAILABLE("90015","账号已通过审核"),
    UNIT_AVAILABLE("90016","往来单位已通过审核"),
    PASSWORD_ERROR("9006","密码格式错误"),
    LOGOUT("9009","退出成功"),
    MOBILE_NUMBER_ERROR("9010","手机号输入错误" ),
    MOBILE_UNAVAILABLE("9019","手机号已被使用" ),

    IMG_ERROR("9100","图片格式错误，仅支持png、jpg、bmp格式的图片"),
    IMG_EMPTY_ERROR("9101","图片不能为空"),
    IMG_SIZE_ERROR("9102","图片大小不能操作1M"),
    IMG_NUM_ERROR("9103","只能上传6张图片"),
    IMG_UPLOAD_ERROR("9104","图片上传失败"),
    IMG_DELETE_ERROR("9105","图片删除错误"),
    IMG_CODE_NEED("9106","图片验证码错误！！！"),

    PAYORDER_FOUND("7001","支付业务单重复创建"),
    PAYORDER_NOTFOUND("7002","没有对应的业务单"),
    PAYORDER_NOTFINISHED("7003","支付业务未成功"),
    PAYORDER_FINISHED("7004","支付业务已成功"),
    PAYORDER_LOCK_ERROR("7005","正在支付中请稍后..."),
    PAYORDER_ERROR("7006","支付失败"),

    PAYCHANNEL_PAY_SUCCESS("7100","交易支付成功"),
    PAYCHANNEL_SET_ERROR("7101","支付渠道账户未创建"),
    PAYCHANNLE_PAY_ERROR("7102","支付渠道返回错误"),
    PAYCHANNLE_PAY_WAIT("7103","等待用户支付"),
    PAYCHANNLE_PAY_CLOSED("7104","未付款交易超时关闭，或支付完成后全额退款"),
    PAYCHANNLE_PAY_FINISHED("7105","交易结束，不可退款"),

    //支付宝授权回调
    PAYCHANNLE_AUTHCODE_SUCCESS("70100","支付宝三方授权成功"),
    PAYCHANNLE_AUTHCODE_ERROR("70101","支付宝三方授权链接获取失败"),




    PAYACCOUNT_IS_UNAVAILABLE("7201","账户不可用"),
    PAYACCOUNT_FOUND("7204","账户已存在"),
    PAYACCOUNT_IS_FROZEN("7202","账户被冻结"),
    PAYACCOUNT_IS_INSUFFICIENT("7203","账户余额不足"),
    PAYACCOUNT_PROFIT_LESS("7205","收益余额不足"),
    PAYACCOUNT_REFUND_LESS("7206","退款金额大于订单金额"),


    PAYACCOUNT_PASSWORD_IS_RIGHT("7210","密码正确" ),
    PAYACCOUNT_PASSWORD_IS_WRONG("7211","密码错误" ),
    PAYACCOUNT_PASSWORD_IS_EMPTY("7212","尚未设置密码" ),


    APP_PAY_NOTIFY_FAIL("app_pay_notify_fail","子系统支付回调接口未设置"),
    REFUND_DATA_FOUND("10007","已经退款"),

    PAYTYPE_WXPAY("wxpay","微信支付"),
    PAYTYPE_ALIPAY("alipay","支付宝支付"),

    APPROVAL_FAIL("approval_fail","有记录没有通过审核"),
    TASK_START("task_is_start","审批已启动"),
    TASK_CANCEL("task_is_cancel","审批已取消"),
    TASK_SUCCESS("task_is_success","审批已成功"),
    TASK_FAIL("task_is_fail","审批未通过"),
    NODE_PASS("node_is_pass","环节已通过"),
    NODE_FAIL("node_is_fail","环节未通过"),



    MEMBER_NULL("member_null","没有对应客户账号"),

    UNIT_IS_NULL("unit_null","没有对应单位账号"),

    UNIT_CERTIFICATE_TYPE_ERROR("unit_certificate_type_error","证件类型只能是:"+ CertificateType.getName()),

    COMPANY_IS_EXIST("1200","企业已创建,请等待审批通过"),
    COMPANY_IS_NULL("1201","企业账户暂时不可用"),

    GOODS_IS_UNAVAILAVLE("1501","商品不可用"),

    LOCK_ERROR("19003","操作失败请稍后再试"),


    SMSCODE_ERROR("smsCode_wrong","短信验证码错误");





    public String code;
    public String tips;
    RespTips(String code, String tips){
        this.code=code;
        this.tips=tips;
    }

}
