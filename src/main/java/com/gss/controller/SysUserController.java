package com.gss.controller;


import com.gss.entity.User;
import com.gss.service.SysUserService;
import com.gss.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sys")
@Api(value = "用户控制器",produces = "application/json")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @ApiOperation(value = "注册",notes = "查询手机号是否存在")
    @RequestMapping(value = "/user/selectMobile/{usMobile}",method = RequestMethod.POST)
    public R selectMobile(@PathVariable Long usMobile){
        return sysUserService.selectMobile(usMobile);
    }



    @ApiOperation(value = "注册",notes = "获取手机验证码")
    @RequestMapping(value = "/user/sendCode/{usMobile}",method = RequestMethod.POST)
    public R getCode(@PathVariable Long usMobile){
        return sysUserService.getCode(usMobile);
    }


    @ApiOperation(value = "注册",notes = "提交注册信息")
    @RequestMapping(value = "/user/register/{code}",method = RequestMethod.POST)
    public R register(@PathVariable int code, User user){
        return sysUserService.register(user,code);
    }


}
