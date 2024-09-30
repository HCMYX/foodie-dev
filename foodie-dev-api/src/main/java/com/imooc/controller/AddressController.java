package com.imooc.controller;

import com.imooc.pojo.*;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.pojo.vo.CommentLevelCountsVo;
import com.imooc.pojo.vo.ItemInfoVo;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.service.AddressService;
import com.imooc.service.ItemService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "地址相关", tags = {"地址相关接口"})
@RestController
@RequestMapping("/address")
public class AddressController extends BaseController {
    @Autowired
    private AddressService addressService;
    /**
     * 用户在确认订单页面，可以针对收货地址做如下操作：
     * 1.查询用户的所有收货地址列表
     * 2.新增收货地址
     * 3.删除收货地址
     * 4.修改收货地址
     * 5.设置默认地址
     */
    @ApiOperation(value = "根据用户id查询收货地址列表", notes = "根据用户id查询收货地址列表", httpMethod = "POST")
    @PostMapping("/list")
    public IMOOCJSONResult list(@RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg("");
        }
        List<UserAddress> list = addressService.queryAll(userId);
        return IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value = "用户新增地址", notes = "用户新增地址", httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add( @RequestBody AddressBO address) {
        IMOOCJSONResult checkResult = checkAddress(address);
        if (checkResult != null && !checkResult.isOK()) {
            return checkResult;
        }
        addressService.addNewUserAddress(address);
        return IMOOCJSONResult.ok();
    }

    private IMOOCJSONResult checkAddress(AddressBO address) {
        String receiver = address.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return IMOOCJSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return IMOOCJSONResult.errorMsg("收货人姓名不能太长");
        }


        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户修改地址", notes = "用户修改地址", httpMethod = "POST")
    @PostMapping("/update")
    public IMOOCJSONResult update(@RequestBody AddressBO address) {
        IMOOCJSONResult checkResult = checkAddress(address);
        if (checkResult != null && !checkResult.isOK()) {
            return checkResult;
        }
        addressService.updateUserAddress(address);
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户删除地址", notes = "用户删除地址", httpMethod = "POST")
    @PostMapping("/delete")
    public IMOOCJSONResult delete(@RequestParam String userId,
                                  @RequestParam String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return IMOOCJSONResult.errorMsg("");
        }
        addressService.deleteUserAddress(userId, addressId);
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户设置默认地址", notes = "用户设置默认地址", httpMethod = "POST")
    @PostMapping("/setDefalut")
    public IMOOCJSONResult setDefalut(@RequestParam String userId,
                                      @RequestParam String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return IMOOCJSONResult.errorMsg("");
        }
        addressService.updateUserAddressToBeDefault(userId, addressId);
        return IMOOCJSONResult.ok();
    }
}
