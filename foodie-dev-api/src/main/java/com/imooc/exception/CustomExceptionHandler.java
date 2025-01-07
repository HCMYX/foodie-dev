package com.imooc.exception;

import com.imooc.utils.IMOOCJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public IMOOCJSONResult handleMaxUploadFile(MaxUploadSizeExceededException exception){
        return IMOOCJSONResult.errorMsg("文件上传大小不能超过500k，请压缩文件");
    }
}
