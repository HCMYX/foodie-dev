package com.imooc.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.imooc.pojo.bo.ShopcartBO;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@Api(value = "购物车接口controller", tags = {"购物车接口controller"})
@RestController
@RequestMapping("/shopcart")
public class ShopcartController extends BaseController {


    @ApiOperation(value = "添加到购物车",notes = "添加购物车",httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(@RequestParam String userId, @RequestBody ShopcartBO shopcartBO,
                               HttpServerRequest request,
                               HttpServerRequest response) {
        if (StringUtils.isBlank(userId)){
            return IMOOCJSONResult.errorMsg("");
        }
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "从购物车中删除商品",notes = "从购物车中删除商品",httpMethod = "POST")
    @PostMapping("/del")
    public IMOOCJSONResult del(@RequestParam String userId, @RequestParam String itemSpecId,
                               HttpServerRequest request,
                               HttpServerRequest response) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)){
            return IMOOCJSONResult.errorMsg("");
        }
        return IMOOCJSONResult.ok();
    }
}
