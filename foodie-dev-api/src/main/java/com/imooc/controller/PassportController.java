package com.imooc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.aspect.SeviceLogAspect;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {
    private static final Logger logger = LoggerFactory.getLogger(PassportController.class);
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在",notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam("username") String username){
        if (StringUtils.isBlank(username)){
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist){
            return IMOOCJSONResult.errorMsg("用户名已存在");
        }
        return IMOOCJSONResult.ok();
    }

    //注册
    @ApiOperation(value = "用户名注册",notes = "用户名注册", httpMethod = "POST")
    @PostMapping("/regist")
    public IMOOCJSONResult regist(@RequestBody UserBO userBO,
                                  HttpServletRequest request,
                                  HttpServletResponse response){
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confimPwd = userBO.getConfirmPassword();

        //判断用户名密码不能为空
        if (StringUtils.isAnyBlank(username,password,confimPwd)){
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }
        //判断用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist){
            return IMOOCJSONResult.errorMsg("用户名已存在");
        }
        //判断两次密码是否一致
        if (!password.equals(confimPwd)){
            return IMOOCJSONResult.errorMsg("密码不一致");
        }
        //密码长度不能少于6位
        if (password.length() < 6){
            return IMOOCJSONResult.errorMsg("密码长度不能小于6");
        }
        //注册
        Users users = userService.createUser(userBO);

        users = setNullProperty(users);
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(users),true);
        return IMOOCJSONResult.ok(users);
    }

    //注册
    @ApiOperation(value = "用户登录",notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public IMOOCJSONResult login(@RequestBody UserBO userBO,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        logger.info("登录接口");
        //判断用户名密码不能为空
        if (StringUtils.isAnyBlank(username,password)){
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }
        Users users = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if (null == users){
            return IMOOCJSONResult.errorMsg("用户名密码错误");
        }
        users = setNullProperty(users);
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(users),true);

        return IMOOCJSONResult.ok(users);
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

    @ApiOperation(value = "用户退出登录",notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(@RequestParam String userId,HttpServletRequest request,
                                  HttpServletResponse response){
        CookieUtils.deleteCookie(request,response,"user");
        return IMOOCJSONResult.ok();
    }
}
