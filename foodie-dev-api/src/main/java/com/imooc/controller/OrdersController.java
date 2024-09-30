package com.imooc.controller;

import cn.hutool.http.HttpStatus;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.bo.SumbitOrderBO;
import com.imooc.pojo.vo.MerchantOrderVO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.OrdersService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Api(value = "订单相关", tags = {"订单相关接口"})
@RestController
@RequestMapping("orders")
public class OrdersController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(OrdersController.class);
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private RestTemplate restTemplate;
    /**
     * 创建订单
     */
    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult create(@RequestBody SumbitOrderBO sumbitOrderBO) {
        if (sumbitOrderBO.getUserId() == null) {
            return IMOOCJSONResult.errorMsg("用户id不能为空");
        }
        if (sumbitOrderBO.getItemSpecIds() == null) {
            return IMOOCJSONResult.errorMsg("商品id不能为空");
        }
        if (sumbitOrderBO.getAddressId() == null) {
            return IMOOCJSONResult.errorMsg("地址id不能为空");
        }
        if (sumbitOrderBO.getPayMethod() == null) {
            return IMOOCJSONResult.errorMsg("支付方式不能为空");
        }
        if (sumbitOrderBO.getPayMethod() != 1 && sumbitOrderBO.getPayMethod() != 2) {
            return IMOOCJSONResult.errorMsg("支付方式不正确");
        }

        OrderVO orderVO = ordersService.createOrder(sumbitOrderBO);
        orderVO.getMerchantOrderVO().setReturnUrl(payReturnUrl);

        MerchantOrderVO merchantOrderVO = orderVO.getMerchantOrderVO();
        merchantOrderVO.setReturnUrl(payReturnUrl);
        merchantOrderVO.setAmount(1);
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId", "99886651");
        headers.add("password", "rt54-3082");
        HttpEntity<MerchantOrderVO> entity = new HttpEntity<>(merchantOrderVO, headers);
        ResponseEntity<IMOOCJSONResult> responseEntity =
                restTemplate.postForEntity(paymentUrl, entity, IMOOCJSONResult.class);
        IMOOCJSONResult payResult =responseEntity.getBody();
        log.info("支付中心返回结果：{}",payResult.getMsg());
        if (payResult.getStatus() != HttpStatus.HTTP_OK) {
            return IMOOCJSONResult.errorMsg("支付中心订单创建失败，请联系管理员");
        }
        return IMOOCJSONResult.ok(orderVO.getOrderId());
    }
    @ApiOperation(value = "支付订单", notes = "支付订单", httpMethod = "POST")
    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        log.info("接收到支付订单回调：{}",merchantOrderId);
        ordersService.updateOrderStatus(merchantOrderId, 20);
        return HttpStatus.HTTP_OK;
    }

    @ApiOperation(value = "查询订单状态", notes = "查询订单状态", httpMethod = "POST")
    @PostMapping("/getPaidOrderInfo")
    public IMOOCJSONResult getPaidOrderInfo(String orderId) {
        return IMOOCJSONResult.ok(ordersService.getPaidOrderInfo(orderId));
    }
}
