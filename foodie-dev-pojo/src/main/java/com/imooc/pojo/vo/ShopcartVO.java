package com.imooc.pojo.vo;

import io.swagger.annotations.ApiModelProperty;

public class ShopcartVO {
    @ApiModelProperty(value = "商品id",name = "itemId",example = "10001",required = true)
    private String itemId;
    @ApiModelProperty(value = "商品图片",name = "itemImgUrl",example = "www.baidu.com",required = true)
    private String itemImgUrl;
    @ApiModelProperty(value = "商品名称",name = "itemName",example = "iphoneX",required = true)
    private String itemName;
    @ApiModelProperty(value = "商品规格id",name = "specId",example = "10001",required = true)
    private String specId;
    @ApiModelProperty(value = "商品规格名称",name = "specName",example = "256G",required = true)
    private String specName;
    @ApiModelProperty(value = "商品价格",name = "priceDiscount",example = "8000",required = true)
    private Integer priceDiscount;
    @ApiModelProperty(value = "商品折扣价格",name = "priceNormal",example = "10000",required = true)
    private Integer priceNormal;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemImgUrl() {
        return itemImgUrl;
    }

    public void setItemImgUrl(String itemImgUrl) {
        this.itemImgUrl = itemImgUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Integer getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(Integer priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public Integer getPriceNormal() {
        return priceNormal;
    }

    public void setPriceNormal(Integer priceNormal) {
        this.priceNormal = priceNormal;
    }
}
