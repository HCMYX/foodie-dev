package com.imooc.controller.center;

import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "用户信息接口", tags = {"用户信息接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController {

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "修改用户信息")
    @PostMapping("update")
    public IMOOCJSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true) String userId,
            @ApiParam(name = "centerUserBO", value = "用户对象", required = true) CenterUserBO centerUserBO) {

        centerUserService.update(userId, centerUserBO);

        return IMOOCJSONResult.ok();
    }

}
