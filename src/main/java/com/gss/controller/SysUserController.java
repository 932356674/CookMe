package com.gss.controller;

import com.gss.entity.User;
import com.gss.service.SysUserService;
import com.gss.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
<<<<<<< HEAD
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
    @RequestMapping(value = "/user/selectMyHome",method = RequestMethod.POST)
    public R selectMyHome( Integer usId){
        return sysUserService.selectMyHome(usId);
    }


    @ApiOperation(value ="修改密码",notes = "修改密码")
    @RequestMapping(value = "/user/updatePassword",method = RequestMethod.POST)
    public R updatePassword(@RequestBody Integer usId,String newPassword,String oldPassword ){
        return sysUserService.updatePassword(usId, newPassword, oldPassword);
    }



=======
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    public R register(@PathVariable int code,@RequestBody User user){
        return sysUserService.register(user,code);
    }


    @ApiOperation(value = "重置密码",notes = "重置密码的验证码")
    @RequestMapping(value = "/user/verifyCode",method = RequestMethod.POST)
    public R verifyCode(@RequestBody Regist regist){
        return sysUserService.verifyCode(regist);
    }


    @ApiOperation(value = "重置密码",notes = "修改密码")
    @RequestMapping(value = "/user/resetPwd",method = RequestMethod.POST)
    public R resetPwd(@RequestBody User user){
        return sysUserService.resetPwd(user);
    }



    @ApiOperation(value = "账号密码码登录",notes = "用户登录")
    @RequestMapping(value = "/user/login",method =RequestMethod.POST )
    public R login(@RequestBody User user){
        String s=null;
        try{
            Subject subject=SecurityUtils.getSubject();
            String pwd=user.getUsPassword();
            Md5Hash md5Hash=new Md5Hash(pwd,user.getUsMobile()+"",1024);
            pwd=md5Hash.toString();
            UsernamePasswordToken token=new UsernamePasswordToken(user.getUsName(),pwd);
            subject.login(token);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();;
            s=e.getMessage();
        }
        return R.error(s);
    }

    @ApiOperation(value = "验证手机号是否存在",notes = "用户登录")
    @RequestMapping(value = "/user/selectMobile",method = RequestMethod.POST)
    public R findMobile(@RequestBody Long phone){
       List<User> list= sysUserService.findMobile(phone);
       if(list.size()>0&&list!=null){
           return sysUserService.getCode(phone);
       }
       return R.error("手机号未注册，请先注册");
    }

    /*@ApiOperation(value = "发送验证码",notes = "用户登录")
    @RequestMapping(value = "/user/gainCodes",method = RequestMethod.POST)
    public R gainCodes(@RequestBody Long phone){
        return sysUserService.getCode(phone);
    }*/

    @ApiOperation(value = "验证验证码登录",notes = "用户登录")
    @RequestMapping(value = "/user/mobileLogin",method = RequestMethod.POST)
    public R mobileLogin(@RequestBody Regist regist){
        return sysUserService.mobileLogin(regist);
    }
>>>>>>> origin/master
}
