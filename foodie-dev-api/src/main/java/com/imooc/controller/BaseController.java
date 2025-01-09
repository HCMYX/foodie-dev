package com.imooc.controller;

import com.imooc.pojo.Orders;
import com.imooc.service.center.MyOrdersService;
import com.imooc.utils.IMOOCJSONResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class BaseController {
    public static final String FOODIE_SHOPCART = "shopcart";
    public static final Integer COMMON_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;
    public static final String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
    public static final String payReturnUrl = "http:///36uqzp.natappfree.cc/orders/notifyMerchantOrderPaid";

    public static final String REDIS_USER_TOKEN = "redis_user_token";
    public static final String REDIS_USER_INFO = "redis_user_info";
    public static final String REDIS_USER_PASSWORD = "redis_user_password";
    public static final String REDIS_USER_FACE = "redis_user_face";
    public static final String REDIS_USER_TOKEN_EXPIRE = "redis_user_token_expire";
    public static final String REDIS_USER_INFO_EXPIRE = "redis_user_info_expire";
    public static final String REDIS_USER_PASSWORD_EXPIRE = "redis_user_password_expire";
    public static final String REDIS_USER_FACE_EXPIRE = "redis_user_face_expire";
    public static final String REDIS_USER_TOKEN_EXPIRE_TIME = "redis_user_token_expire_time";
    public static final String REDIS_USER_INFO_EXPIRE_TIME = "redis_user_info_expire_time";
    public static final String REDIS_USER_PASSWORD_EXPIRE_TIME = "redis_user_password_expire_time";
    public static final String REDIS_USER_FACE_EXPIRE_TIME = "redis_user_face_expire_time";
    public static final String REDIS_USER_TOKEN_EXPIRE_TIME_KEY = "redis_user_token_expire_time_key";
    public static final String REDIS_USER_INFO_EXPIRE_TIME_KEY = "redis_user_info_expire_time_key";
    public static final String REDIS_USER_PASSWORD_EXPIRE_TIME_KEY = "redis_user_password_expire_time_key";
    public static final String REDIS_USER_FACE_EXPIRE_TIME_KEY = "redis_user_face_expire_time_key";
    public static final String REDIS_USER_TOKEN_EXPIRE_TIME_VALUE = "redis_user_token_expire_time_value";
    public static final String REDIS_USER_INFO_EXPIRE_TIME_VALUE = "redis_user_info_expire_time_value";
    public static final String REDIS_USER_PASSWORD_EXPIRE_TIME_VALUE = "redis_user_password_expire_time_value";
    public static final String REDIS_USER_FACE_EXPIRE_TIME_VALUE = "redis_user_face_expire_time_value";
    public static final String REDIS_USER_TOKEN_EXPIRE_TIME_VALUE_KEY = "redis_user_token_expire_time_value_key";
    public static final String REDIS_USER_INFO_EXPIRE_TIME_VALUE_KEY = "redis_user_info_expire_time_value_key";
    public static final String REDIS_USER_PASSWORD_EXPIRE_TIME_VALUE_KEY = "redis_user_password_expire_time_value_key";
    public static final String REDIS_USER_FACE_EXPIRE_TIME_VALUE_KEY = "redis_user_face_expire_time_value_key";
    @Autowired
    public MyOrdersService myOrdersService;

    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     * @return
     */
    public IMOOCJSONResult checkUserOrder(String userId, String orderId) {
        Orders order = myOrdersService.queryMyOrder(userId, orderId);
        if (order == null) {
            return IMOOCJSONResult.errorMsg("订单不存在！");
        }
        return IMOOCJSONResult.ok(order);
    }
}
