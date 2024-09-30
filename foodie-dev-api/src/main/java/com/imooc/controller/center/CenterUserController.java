package com.imooc.controller.center;

import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "用户信息接口", tags = {"用户信息接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController {

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "修改用户信息")
    @PostMapping("update")
    public IMOOCJSONResult update(
            @RequestParam String userId,
            @RequestBody CenterUserBO centerUserBO,
            HttpServletRequest request, HttpServletResponse response) {

        Users users = centerUserService.updateUserInfo(userId, centerUserBO);
        users = setNullProperty(users);
        CookieUtils.setCookie(request, response,"user", JsonUtils.objectToJson(users),true);
        return IMOOCJSONResult.ok();
    }
    private Users setNullProperty(Users users){
        users.setPassword(null);
        users.setRealname(null);
        users.setEmail(null);
        users.setCreatedTime(null);
        users.setUpdatedTime(null);
        users.setBirthday(null);
        return users;
    }
}
