package com.otzg.alipay.redbag.entity;

import java.io.Serializable;

public class Result<T> implements Serializable {

    /**  */
    private static final long serialVersionUID = -3288962446305233126L;
    
    /** 是否处理成功 */
    private boolean           isSuccess        = false;

    /** 非业务错误码，仅在<code>isSuccess==false</code>时才有效 */
    private String            errorCode;

    /**返回信息*/
    protected String          message;

    /**要返回的数据类型*/
    private T                 value;

    /**
     * Getter method for property <tt>isSuccess</tt>.
     *
     * @return property value of isSuccess
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * Setter method for property <tt>isSuccess</tt>.
     *
     * @param isSuccess value to be assigned to property isSuccess
     */
    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    /**
     * Getter method for property <tt>errorCode</tt>.
     *
     * @return property value of errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Setter method for property <tt>errorCode</tt>.
     *
     * @param errorCode value to be assigned to property errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Getter method for property <tt>message</tt>.
     *
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>message</tt>.
     *
     * @param message value to be assigned to property message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter method for property <tt>value</tt>.
     *
     * @return property value of value
     */
    public T getValue() {
        return value;
    }

    /**
     * Setter method for property <tt>value</tt>.
     *
     * @param value value to be assigned to property value
     */
    public void setValue(T value) {
        this.value = value;
    }

}
