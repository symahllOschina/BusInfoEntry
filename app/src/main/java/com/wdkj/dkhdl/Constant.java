package com.wdkj.dkhdl;
/**
 * 常量管理
 */
public class Constant {

    /**
     * netTypeList的值分别对应"QY","GT","XW"
     */
    public static final String netTypeList[] = {"企业","个体工商户"};
    public static final String netTypeStrList[] = {"QY","GT"};

    public static final String getNetType(String str){
        if(str.equals(netTypeList[0])){
            return netTypeStrList[0];
        }else if(str.equals(netTypeList[1])){
            return netTypeStrList[1];
        }
        return "";
    }


    public static final int getNetTypeIndex(String str){
        if(str.equals(netTypeStrList[0])){
            return 0;
        }else if(str.equals(netTypeStrList[1])){
            return 1;
        }
        return 0;
    }

    public static final String netLicenseTypeList[] = {"三证合一","营业执照"};
    public static final String netLicenseStrTypeList[] = {"SZHY","YYZZ"};

    public static final String getNetLicenseType(String str){
        if(str.equals(netLicenseTypeList[0])){
            return netLicenseStrTypeList[0];
        }else if(str.equals(netLicenseTypeList[1])){
            return netLicenseStrTypeList[1];
        }
        return "";
    }

    public static final int getNetLicenseTypeIndex(String str){
        if(str.equals(netLicenseStrTypeList[0])){
            return 0;
        }else if(str.equals(netLicenseStrTypeList[1])){
            return 1;
        }
        return 0;
    }



}
