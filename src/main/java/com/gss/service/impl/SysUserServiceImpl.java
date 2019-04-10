package com.gss.service.impl;

import com.gss.entity.User;
import com.gss.entity.UserExample;
import com.gss.mapper.UserMapper;
import com.gss.service.SysUserService;
import com.gss.utils.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {


    @Resource
    private UserMapper userMapper;

    @Override
    public R selectMobile(Long mobile) {

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        criteria.andUsMobileEqualTo(mobile);

        List<User> users = userMapper.selectByExample(example);

        System.out.println(users);

        if(users.isEmpty()){
            return R.ok("手机号可用");
        }else {
            return R.error("该手机号已注册");
        }

    }




}
