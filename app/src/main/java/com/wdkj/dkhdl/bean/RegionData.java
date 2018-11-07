package com.wdkj.dkhdl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 省份
 */
public class RegionData implements Serializable{

    private Integer id;//: 1,
    private Integer fid;//: 0,
    private Integer sid;//: 110000,
    private String name;//: null,
    private String fullname;//: "北京市",
    private String pinyin;//: null,
    private String lng;//: null,
    private String lat;//: null,
    private String level;//: 0

    private List<RegionData> subList;//对应的下面的子菜单




    public RegionData() {
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

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<RegionData> getSubList() {
        return subList;
    }

    public void setSubList(List<RegionData> subList) {
        this.subList = subList;
    }
}