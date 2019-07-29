package com.wdkj.dkhdl.bean;

import java.io.Serializable;
/**
 * 行业类目
 */
public class IndustryTypeData implements Serializable{

    private Integer id;//: 1,
    private Integer fid;//: 0,
    private Integer tid;//: null,
    private String name;// "企业"



    public IndustryTypeData() {
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

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
