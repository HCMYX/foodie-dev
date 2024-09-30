package com.imooc.pojo.bo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


public class SumbitOrderBO {
    @ApiModelProperty(value = "用户id", name = "userId", example = "imooc", required = true)
    private String userId;
    @ApiModelProperty(value = "订单明细", name = "items", example = "1", required = true)
    private String itemSpecIds;
    @ApiModelProperty(value = "收货地址id", name = "addressId", example = "imooc", required = true)
    private String addressId;
    @ApiModelProperty(value = "支付方式", name = "payMethod", example = "1", required = true)
    private Integer payMethod;
    @ApiModelProperty(value = "订单备注", name = "leftMsg", example = "1", required = false)
    private String leftMsg;
    private Date createdTime;
    private Date updatedTime;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemSpecIds() {
        return itemSpecIds;
    }

    public void setItemSpecIds(String itemSpecIds) {
        this.itemSpecIds = itemSpecIds;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getLeftMsg() {
        return leftMsg;
    }

    public void setLeftMsg(String leftMsg) {
        this.leftMsg = leftMsg;
    }

}
