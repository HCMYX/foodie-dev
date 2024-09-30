package com.imooc.service;

import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.pojo.bo.SumbitOrderBO;
import com.imooc.pojo.vo.OrderVO;

import java.util.List;

public interface OrdersService {
      public OrderVO createOrder(SumbitOrderBO sumbitOrderBO);

      public void updateOrderStatus(String orderId,Integer orderStatus);

      public OrderStatus getPaidOrderInfo(String orderId);
}
