package com.wdkj.dkhdl.bean;

import java.io.Serializable;

/**
 * 银行总行
 */
public class BackListData implements Serializable{

    private Integer id;//: 1,
    private Integer fid;//: 0,
    private String idno;// "103",
    private String bank_no;//: "103100000026",
    private String bank_name;//: "中国农业银行",
    private String city_code;//: "石家庄市",
    private String address;//: null,
    private String province_code;//: "河北省"
    private String head_bank;//"国家开发银行"


    public BackListData() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getBank_no() {
        return bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    public String getHead_bank() {
        return head_bank;
    }

    public void setHead_bank(String head_bank) {
        this.head_bank = head_bank;
    }
}
