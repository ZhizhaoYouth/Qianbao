package com.finplat.financialmanage.controller;

import com.finplat.financialmanage.annotation.AuthCheck;
import com.finplat.financialmanage.common.BaseResponse;
import com.finplat.financialmanage.common.ResultUtils;
import com.finplat.financialmanage.constant.UserConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @GetMapping("/hello")
    public BaseResponse<String> hello(){
        return ResultUtils.success("hello world");
    }
    @GetMapping("/helloAdmin")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<String> helloAdmin(){
        return ResultUtils.success("hello admin");
    }
}
