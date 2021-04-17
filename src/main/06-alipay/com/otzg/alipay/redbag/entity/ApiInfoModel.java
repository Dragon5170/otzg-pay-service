package com.otzg.alipay.redbag.entity;

import com.otzg.alipay.redbag.util.StringUtil;

import java.util.List;

public class ApiInfoModel {
    
    /**
     * 接口请求
     */
    public static final Integer INVOKE_TYPE_REQUEST = 1;
    /**
     * 页面跳转
     */
    public static final Integer INVOKE_TYPE_REDIRECT = 2;
    
    private String apiName;
    private String apiZhName;
    /**
     * 字段说明
     * 1:接口请求
     * 2：页面跳转
     */
    private Integer invokeType;

    private List<ApiParamModel> apiInParam;
    private List<ApiParamModel> apiOutParam;

    
    public Integer getInvokeType() {
        return invokeType;
    }

    public void setInvokeType(Integer invokeType) {
        this.invokeType = invokeType;
    }

    public String getApiName() {
        return apiName;
    }

    public String getApiNameFirstUpper(){
        if(apiName!=null){
            return StringUtil.transToFirstUpper(apiName);
        }
        return null;
    }

    public String getApiNameFirstLower(){
        if(apiName!=null){
            return StringUtil.transToFirstLower(apiName);
        }
        return null;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public List<ApiParamModel> getApiInParam() {
        return apiInParam;
    }

    public void setApiInParam(List<ApiParamModel> apiInParam) {
        this.apiInParam = apiInParam;
    }

    public List<ApiParamModel> getApiOutParam() {
        return apiOutParam;
    }

    public void setApiOutParam(List<ApiParamModel> apiOutParam) {
        this.apiOutParam = apiOutParam;
    }

    public String getApiZhName() {
        return apiZhName;
    }

    public void setApiZhName(String apiZhName) {
        this.apiZhName = apiZhName;
    }


}
