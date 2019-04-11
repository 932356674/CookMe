package com.gss.controller;

import com.gss.entity.User;
import com.gss.service.SysUserService;
import com.gss.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@RequestMapping("/sys")
@Api(value = "个人信息" ,produces = "application/json")
@RestController
public class SysUserController {
    @Resource
    private SysUserService sysUserService;

    @ApiOperation(value ="修改个人信息",notes = "修改个人信息")
    @RequestMapping(value = "/user/changeInfoById",method = RequestMethod.POST)
    public R changeInfoById(@RequestBody User user){
        return sysUserService.changeInfoById(user);
    }

    @ApiOperation(value ="个人主页",notes = "个人主页")
    @RequestMapping(value = "/user/selectMyHome/{usId}",method = RequestMethod.POST)
    public R selectMyHome( Integer usId){
        return sysUserService.selectMyHome(usId);
    }


    @ApiOperation(value ="修改密码",notes = "修改密码")
    @RequestMapping(value = "/user/updatePassword",method = RequestMethod.POST)
    public R updatePassword(@RequestBody Integer usId,String newPassword,String oldPassword ){
        return sysUserService.updatePassword(usId, newPassword, oldPassword);
    }



}
