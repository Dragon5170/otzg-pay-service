package com.otzg.util;

/**
 * @author G./2018/6/5 17:32
 */
public enum CertificateType {
    法人证件,营业执照,组织机构,行业资质;

    //判断是否在枚举类型内的值
    public final static boolean hasNotFound(String value){
        try{
            CertificateType.valueOf(value);
            return false;
        }catch (Exception e){
            return true;
        }
    }

    public final static String getName() {
        String names="";
        for(CertificateType ct:CertificateType.values()){
            names += ","+ct.name();
        }
        return FuncUtil.CleanComma(names);
    }

//    public static void main(String[] args){
//        LogUtil.print(CertificateType.法人证件.name());
//    }
}
