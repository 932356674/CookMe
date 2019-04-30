package com.gss.service;

import com.gss.entity.User;
import com.gss.utils.R;
import com.gss.entity.Regist;
import com.gss.entity.User;
import com.gss.utils.R;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface SysUserService {
    //个人主页
    public R selectMyHome(Integer usId);
    //完善个人信息
    public R changeInfoById(User user);
    //修改密码
    public R updatePassword(String oldPassword,String newPasswords);

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
//首页获取推荐用户
    R selectBest();
//验证手机号是否存在（跟注册的验证结果相反）
    List<User> findMobile(long phone);
//用户名密码登录
    User login(long phone);
//短信验证登录
    R mobileLogin(Regist regist);

    //查询手机号是否存在验证码表
    Regist findPhone(long phone);

//修改个人头像
    public R updateHead(MultipartFile file);

//他人主页
    public R selectHeHome(Integer usId,Integer showId);
}
