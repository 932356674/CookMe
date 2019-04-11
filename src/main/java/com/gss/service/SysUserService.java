package com.gss.service;

import com.gss.entity.User;
import com.gss.utils.R;

public interface SysUserService {
    public R selectMyHome(Integer usId);

    public R changeInfoById(User user);

    public R updatePassword(Integer usId,String oldPassword,String newPasswords);
}
