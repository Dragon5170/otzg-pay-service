package com.otzg.alipay.redbag.entity;

import java.util.ArrayList;
import java.util.List;

public class ApiParamModel {
    /**
     * 基础类型
     */
    public static final int TYPE_BASETYPE = 0;
    /**
     * 复杂类型
     */
    public static final int TYPE_COMPLEXTYPE = 1;
    /** API参数模型列表*/
    private List<ApiParamModel> childs = new ArrayList<ApiParamModel>();

    /** 是否列表类型 */
    private boolean       isListType;

    /** 是否必须,非空 */
    private int           isMust;

    /** 类型 */
    private int baseType;
    
    private String description;


    private String                   enName;
    private String                   fullParamName;
    private String                      title;
    private String                      desc;

    private String defaultValue;

    public List<ApiParamModel> getChilds() {
        return childs;
    }

    public void setChilds(List<ApiParamModel> childs) {
        this.childs = childs;
    }


    public String getFullParamName() {
        return fullParamName;
    }

    public void setFullParamName(String fullParamName) {
        this.fullParamName = fullParamName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean getIsListType() {
        return isListType;
    }


    public boolean isListType() {
        return isListType;
    }

    public void setIsListType(boolean listType) {
        isListType = listType;
    }

    public int getIsMust() {
        return isMust;
    }

    public void setIsMust(int isMust) {
        this.isMust = isMust;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public int getBaseType() {
        return baseType;
    }

    public void setBaseType(int baseType) {
        this.baseType = baseType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
