package com.gss.service.impl;

import com.gss.entity.All;
import com.gss.entity.Collect;
import com.gss.entity.Cookbook;
import com.gss.entity.User;
import com.gss.mapper.CollectMapper;
import com.gss.mapper.CookbookMapper;
import com.gss.mapper.UserMapper;
import com.gss.service.SysUserService;
import com.gss.utils.R;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service(value = "sysUserServiceImpl")
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private CookbookMapper cookbookMapper;
    @Resource
    private CollectMapper collectMapper;
    @Override
    public R selectMyHome(Integer usId) {
        User user = userMapper.selectByPrimaryKey(usId);
        //分享的菜谱
        List<Cookbook> list = cookbookMapper.selectCookbookByUsId(usId);

        //收藏的菜谱
        List<Collect> list1 = collectMapper.selectCookbookByUsId(usId);

        return R.ok().put("user",user).put("cookbook1",list).put("cookbook2",list1);
    }

    @Override
    public R changeInfoById(User user) {
        int i = userMapper.updateByPrimaryKeySelective(user);
        if (i>0){
            return R.ok();
        }
        return R.error("完善失败");
    }

    @Override
    public R updatePassword(Integer usId, String newPassword,String oldPassword) {
        User user = userMapper.selectByPrimaryKey(usId);
        Md5Hash oldPasswords = new Md5Hash(oldPassword,user.getUsMobile()+"",1024);
        oldPassword = oldPasswords.toString();
        Md5Hash newPasswords = new Md5Hash(newPassword,user.getUsMobile()+"",1024);
        newPassword = newPasswords.toString();
        System.out.println(oldPassword+"旧密码");
        System.out.println(newPassword+"新密码");
        if (oldPassword.equals(user.getUsPassword())){
            user.setUsPassword(newPassword);
            int i = userMapper.updateByPrimaryKey(user);
            if (i>0){
                return R.ok();
            }
        }
        return R.error("修改失败");
    }

}
