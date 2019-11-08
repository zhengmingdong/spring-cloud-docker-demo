package com.zynn.service.module.user.controller;

import com.zynn.common.pojo.result.Result;
import com.zynn.common.pojo.result.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.zynn.common.core.constant.ApiPathConstant.PATH_PREFIX_V1;
import static com.zynn.common.core.constant.ApiPathConstant.PATH_V1;

/**
 * @author zhanghao
 * @date 2019/5/23 16:56
 **/
@Slf4j
@Validated
@RestController
@Api(tags = "用户登录相关接口")
@RequestMapping({PATH_PREFIX_V1 + "/auth",PATH_V1+"/auth"})
public class UserLoginController {


    @PostMapping("/accredit/login")
    @ApiOperation(value = "微信/QQ登录(授权登录)", notes = "王宇林")
    public Result accreditLogin() {
        return ResultUtil.success();
    }


    @GetMapping("/cellphone/login/{cellphone}")
    @ApiOperation(value = "手机登录",  notes = "王宇林")
    public Result loginOrRegister() {
        return ResultUtil.success();
    }


}
