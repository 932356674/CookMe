package com.gss.service;

import com.gss.entity.User;
import com.gss.utils.R;

public interface SysUserService {
    //个人主页
    public R selectMyHome(Integer usId);
    //完善个人信息
    public R changeInfoById(User user);
    //修改密码
    public R updatePassword(Integer usId,String oldPassword,String newPasswords);
}
