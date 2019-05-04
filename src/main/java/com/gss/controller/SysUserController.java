package com.gss.controller;


import com.gss.entity.Regist;
import com.gss.entity.User;
import com.gss.service.SysUserService;
import com.gss.utils.R;
import com.gss.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RequestMapping("/sys")
@CrossOrigin(origins = {"*"})
@Api(value = "个人信息" ,produces = "application/json")
@RestController
public class SysUserController {
    @Resource
    private SysUserService sysUserService;

    @ApiOperation(value ="修改个人信息",notes = "修改个人信息")
    @RequestMapping(value = "/user/changeInfoById",method = RequestMethod.PUT)
    public R changeInfoById(@RequestBody User user){
        return sysUserService.changeInfoById(user);
    }

    @ApiOperation(value ="个人主页",notes = "个人主页")
    @RequestMapping(value = "/user/selectMyHome",method = RequestMethod.GET)
    public R selectMyHome(){
        return sysUserService.selectMyHome(ShiroUtils.getUserId());
    }


    @ApiOperation(value ="修改密码",notes = "修改密码")
    @RequestMapping(value = "/user/updatePassword",method = RequestMethod.PUT)
    public R updatePassword(@RequestParam("newPassword") String newPassword,@RequestParam("oldPassword") String oldPassword ){
        return sysUserService.updatePassword(newPassword, oldPassword);
    }

    @ApiOperation(value = "注册",notes = "查询手机号是否存在")
    @RequestMapping(value = "/user/selectMobile", method = RequestMethod.POST)
    public R selectMobile(@RequestBody User user){
        System.out.println(user.getUsMobile());
        return sysUserService.selectMobile(user.getUsMobile());
    }

    @ApiOperation(value = "注册",notes = "获取手机验证码")
    @RequestMapping(value = "/user/sendCode/{usMobile}",method = RequestMethod.GET)
    public R getCode(@PathVariable Long usMobile){
        return sysUserService.getCode(usMobile);
    }

    @ApiOperation(value = "注册",notes = "提交注册信息")
    @RequestMapping(value = "/user/register/{code}",method = RequestMethod.POST)
    public R register(@PathVariable Integer code,@RequestBody User user){
        return sysUserService.register(user,code);
    }


    @ApiOperation(value = "重置密码",notes = "重置密码的验证码")
    @RequestMapping(value = "/user/verifyCode",method = RequestMethod.GET)
    public R verifyCode(@RequestParam("usMobile") Long usMobile,@RequestParam("code") Integer code){
        Regist regist = new Regist();
        regist.setPhone(usMobile);
        regist.setCode(code);
        return sysUserService.verifyCode(regist);
    }

    @ApiOperation(value = "重置密码",notes = "修改密码")
    @RequestMapping(value = "/user/resetPwd",method = RequestMethod.PUT)
    public R resetPwd(@RequestBody User user){
        return sysUserService.resetPwd(user);
    }

    @ApiOperation(value = "首页获取用户信息",notes = "首页随机获取用户信息")
    @RequestMapping(value = "/user/selectBest",method = RequestMethod.POST)
    public R selectBest(){
        return sysUserService.selectBest();
    }

    @ApiOperation(value = "账号密码登录",notes = "用户登录")
    @RequestMapping(value = "/user/login",method =RequestMethod.POST )
    public R login(@RequestBody User user){
        String s="登录失败，请重试！";
        try{
            Subject subject=SecurityUtils.getSubject();
            String pwd=user.getUsPassword();
            Md5Hash md5Hash=new Md5Hash(pwd,user.getUsMobile()+"",1024);
            pwd=md5Hash.toString();
            User user1=sysUserService.login(user.getUsMobile());
            if(!pwd.equals(user1.getUsPassword())){
                throw  new IncorrectCredentialsException("密码错误");
            }
            UsernamePasswordToken token=new UsernamePasswordToken(String.valueOf(user.getUsMobile()),pwd);
            subject.login(token);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            s=e.getMessage();
        }
        return R.error(s);
    }

    @ApiOperation(value = "验证手机号是否存在",notes = "用户登录")
    @RequestMapping(value = "/user/findMobile",method = RequestMethod.GET)
    public R findMobile(@RequestParam("usMobile") Long usMobile){
        List<User> list= sysUserService.findMobile(usMobile);
        if(list.size()>0&&list!=null){
            return sysUserService.getCode(usMobile);
        }
        return R.error("手机号未注册，请先注册");
    }

    @ApiOperation(value = "发送验证码",notes = "用户登录")
    @RequestMapping(value = "/user/gainCodes",method = RequestMethod.GET)
    public R gainCodes(@RequestParam("usMobile") Long usMobile){
        return sysUserService.getCode(usMobile);
    }

    @ApiOperation(value = "验证验证码登录",notes = "用户登录")
    @RequestMapping(value = "/user/mobileLogin",method = RequestMethod.GET)
    public R mobileLogin(@RequestParam("usMobile") Long usMobile,@RequestParam("code") Integer code){
        String s="登录失败，请重试！";
        try{
            Subject subject=SecurityUtils.getSubject();
            Regist regist=sysUserService.findPhone(usMobile);
            if(regist==null){
                throw new UnknownAccountException("手机号未注册");
            }
            if(!code.equals(regist.getCode())){
                throw  new IncorrectCredentialsException("验证码错误");
            }
            UsernamePasswordToken token=new UsernamePasswordToken(String.valueOf(regist.getPhone()),String.valueOf(code));
            subject.login(token);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            s=e.getMessage();
        }
        return R.error(s);
    }


    @ApiOperation(value = "修改个人头像",notes = "修改头像")
    @RequestMapping(value = "/user/updateHead",method = RequestMethod.POST)
    public Map<String,Object> updateHead(@RequestBody MultipartFile file){
        return sysUserService.updateHead(file);
    }


    @ApiOperation(value ="他人主页",notes = "他人主页")
    @RequestMapping(value = "/user/selectHeHome",method = RequestMethod.GET)
    public R selectHeHome(@RequestParam("usId") Integer usId){

        return sysUserService.selectHeHome(usId,ShiroUtils.getUserId());
    }


    @ApiOperation(value ="个人信息",notes = "个人信息")
    @RequestMapping(value = "/user/selectById",method = RequestMethod.GET)
    public R selectById(){
        int usId=ShiroUtils.getUserId();
        return sysUserService.selectUserById(usId);
    }


    @ApiOperation(value = "用户退出",notes = "用户退出")
    @RequestMapping(value = "/user/logout",method = RequestMethod.POST)
    public R logout(){
        //清空session
        ShiroUtils.logout();
        return R.ok();
    }
}
