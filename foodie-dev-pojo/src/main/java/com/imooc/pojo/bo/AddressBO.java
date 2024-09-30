package com.imooc.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


public class AddressBO {
    @ApiModelProperty(value = "用户id", name = "userId", example = "imooc", required = true)
    private String userId;
    @ApiModelProperty(value = "地址id", name = "addressId", example = "imooc", required = false)
    private String addressId;
    @ApiModelProperty(value = "收货人姓名", name = "receiver", example = "imooc", required = true)
    private String receiver;
    @ApiModelProperty(value = "收货人手机号", name = "mobile", example = "imooc", required = true)
    private String mobile;
    @ApiModelProperty(value = "省份", name = "province", example = "imooc", required = true)
    private String province;
    @ApiModelProperty(value = "城市", name = "city", example = "imooc", required = true)
    private String city;
    @ApiModelProperty(value = "区县", name = "district", example = "imooc", required = true)
    private String district;
    @ApiModelProperty(value = "详细地址", name = "detail", example = "imooc", required = true)
    private String detail;
    @ApiModelProperty(value = "是否是默认地址", name = "isDefault", example = "imooc", required = false)
    private Integer isDefault;
    @ApiModelProperty(value = "扩展字段", name = "extand", example = "imooc", required = false)
    private String extand;
    @ApiModelProperty(value = "创建时间", name = "createdTime", example = "imooc", required = false)
    private Date createdTime;
    @ApiModelProperty(value = "更新时间", name = "updatedTime", example = "imooc", required = false)
    private Date updatedTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public String getExtand() {
        return extand;
    }

    public void setExtand(String extand) {
        this.extand = extand;
    }

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
}
