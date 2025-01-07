package com.imooc.controller.center;

import com.google.common.collect.Maps;
import com.imooc.controller.BaseController;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.resource.FileUpload;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.DateUtil;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Api(value = "用户信息接口", tags = {"用户信息接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private FileUpload fileUpload;

    @ApiOperation(value = "修改用户信息")
    @PostMapping("update")
    public IMOOCJSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @RequestBody @Valid CenterUserBO centerUserBO,
            BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        //判断BindingResult是否保存错误信息
        if (result.hasErrors()) {
            Map<String,String> errorResult =getErrorMap(result);
            return IMOOCJSONResult.errorMap(errorResult);
        }
        Users users = centerUserService.updateUserInfo(userId, centerUserBO);
        users = setNullProperty(users);
        CookieUtils.setCookie(request, response,"user", JsonUtils.objectToJson(users),true);
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户头像修改")
    @PostMapping("uploadFace")
    public IMOOCJSONResult uploadFace(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true) MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        //文件保存地址
        String fileSpace = fileUpload.getImageUserFaceLocation();
        String upLoadPathPrefix = File.separator +userId;
        //判断文件是否为空
        if (file.isEmpty()) {
            return IMOOCJSONResult.errorMsg("文件不能为空");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        String fileNameArr[] = fileName.split("\\.");
        String suffix = fileNameArr[fileNameArr.length - 1];
        if (!suffix.equalsIgnoreCase("png") &&
                !suffix.equalsIgnoreCase("jpg") &&
                !suffix.equalsIgnoreCase("jpeg")){
            return IMOOCJSONResult.errorMsg("图片格式不正确");
        }
        // face-{userId}.png
        String newFileName = "face-"+ userId + "." + suffix;

        //上传的头像保存地址
        String finalFacePath = fileSpace + upLoadPathPrefix + File.separator + newFileName;
        File outFile = new File(finalFacePath);
        if (outFile.getParentFile() != null) {
            outFile.getParentFile().mkdirs();
        }
        //保存文件
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outFile);
            InputStream inputStream = file.getInputStream();
            IOUtils.copy(inputStream,outputStream);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (outputStream != null){
                outputStream.flush();
                outputStream.close();
            }
        }
        //用于提供给web服务访问的地址
        String finalFacePathWeb = fileUpload.getImageServerUrl() + userId + "/" + newFileName
                    //浏览器可能存在缓存，在地址最后加上时间戳，保证图片可以刷新
                    + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
        Users usersResult = centerUserService.updateUserFace(userId,finalFacePathWeb);

        return IMOOCJSONResult.ok();
    }
    private Map<String,String> getErrorMap(BindingResult result){
        Map<String,String> map = Maps.newHashMap();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            String field = error.getField();
            String defaultMessage = error.getDefaultMessage();
            map.put(field,defaultMessage);
        }
        return map;
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
