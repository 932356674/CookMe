package com.gss.service;

import com.gss.entity.Regist;
import com.gss.entity.User;
import com.gss.utils.R;

import java.util.List;

public interface SysUserService {

//查询用户手机号是否存在
    public R selectMobile(Long mobile);

//通过前端手机号获取验证码发送手机并存到数据库中
    public R getCode(Long mobile);

//验证用户输入的验证码是否正确并提交注册信息
    public R register(User user,int code);

//验证重置密码时的验证码是否正确
    public R verifyCode(Regist regist);

//重置密码
    public R resetPwd(User user);

//验证手机号是否存在（跟注册的验证结果相反）
    List<User> findMobile(long phone);
//用户名密码登录
    User login(long phone);
//短信验证登录
    R mobileLogin(Regist regist);

}
