package com.imooc.service.impl;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.mapper.UserAddressMapper;
import com.imooc.pojo.*;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.pojo.bo.SumbitOrderBO;
import com.imooc.pojo.vo.MerchantOrderVO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.AddressService;
import com.imooc.service.ItemService;
import com.imooc.service.OrdersService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private ItemService itemService;
    @Autowired
    private Sid sid;
    @Autowired
    private AddressService addressService;
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(SumbitOrderBO sumbitOrderBO) {
        String userId = sumbitOrderBO.getUserId();
        String addressId = sumbitOrderBO.getAddressId();
        String itemSpecId = sumbitOrderBO.getItemSpecIds();
        Integer payMethod = sumbitOrderBO.getPayMethod();
        String leftMsg = sumbitOrderBO.getLeftMsg();
        Integer postAmount = 0;
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addressId);

        if (YesOrNo.YES.type == payMethod) {
            postAmount = 0;
        }
        String orderId = sid.nextShort();
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setUserId(userId);
        orders.setReceiverName(userAddress.getReceiver());
        orders.setReceiverMobile(userAddress.getMobile());
        orders.setReceiverAddress(userAddress.getProvince() + " " + userAddress.getCity() + " " + userAddress.getDistrict() + " " + userAddress.getDetail());
        //orders.setTotalAmount(sumbitOrderBO.getOrderTotal());
        //orders.setRealPayAmount(sumbitOrderBO.getRealPayAmount());
        orders.setPostAmount(postAmount);
        orders.setPayMethod(payMethod);
        orders.setLeftMsg(leftMsg);
        orders.setIsComment(YesOrNo.NO.type);
        orders.setIsDelete(YesOrNo.NO.type);
        orders.setCreatedTime(new Date());
        orders.setUpdatedTime(new Date());

        //2.
        String itemSpecIdArr[] = itemSpecId.split(",");
        Integer totalAmount = 0;
        Integer realPayAmount = 0;

        for (String itemSpecIdStr : itemSpecIdArr) {
            int buyCounts = 1;
            ItemsSpec itemsSpec = itemService.queryItemSpecById(itemSpecIdStr);
            totalAmount += itemsSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;
            //
            String itemId = itemsSpec.getItemId();
            Items items = itemService.queryItemById(itemId);
            String itemImgUrl = itemService.queryItemMainImgById(itemId);

            OrderItems orderItems = new OrderItems();
            orderItems.setId(sid.nextShort());
            orderItems.setOrderId(orderId);
            orderItems.setItemId(itemId);
            orderItems.setItemImg(itemImgUrl);
            orderItems.setItemName(items.getItemName());
            orderItems.setItemSpecId(itemSpecIdStr);
            orderItems.setItemSpecName(itemsSpec.getName());
            orderItems.setBuyCounts(buyCounts);
            orderItems.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(orderItems);
            itemService.decreaseItemSpecStock(itemSpecIdStr,buyCounts);
        }
        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPayAmount);
        ordersMapper.insert(orders);
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        orderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(orderStatus);

        MerchantOrderVO merchantOrderVO = new MerchantOrderVO();
        merchantOrderVO.setMerchantOrderId(orderId);
        merchantOrderVO.setMerchantUserId(userId);
        merchantOrderVO.setAmount(realPayAmount + postAmount);
        merchantOrderVO.setPayMethod(payMethod);
        //merchantOrderVO.setReturnUrl();

        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrderVO(merchantOrderVO);
        return orderVO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatus getPaidOrderInfo(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }
}
