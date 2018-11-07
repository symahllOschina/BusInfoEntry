package com.wdkj.dkhdl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 商户列表
 */
public class BusListData implements Serializable{


    private Integer numPerPage;//: 5,
    private Integer totalCount;//: 2,
    private Integer pageNum;//: 1

    private List<BusDetailData> shopList;

    public BusListData() {

    }

    public Integer getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public List<BusDetailData> getShopList() {
        return shopList;
    }

    public void setShopList(List<BusDetailData> shopList) {
        this.shopList = shopList;
    }
}
