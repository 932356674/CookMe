package com.gss.service.impl;

import com.gss.entity.Regist;
import com.gss.entity.RegistExample;
import com.gss.entity.User;
import com.gss.entity.UserExample;
import com.gss.mapper.RegistMapper;
import com.gss.mapper.UserMapper;
import com.gss.service.SysUserService;
import com.gss.utils.GetMessageCode;
import com.gss.utils.R;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {


    @Resource
    private UserMapper userMapper;

    @Resource
    private RegistMapper registMapper;

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

    @Override
    public R getCode(Long mobile) {

        RegistExample registExample = new RegistExample();

        RegistExample.Criteria criteria = registExample.createCriteria();

        criteria.andPhoneEqualTo(mobile);
        //查询数据库中是否是第一次获取验证码
        List<Regist> regist1 = registMapper.selectByExample(registExample);
        //向手机发送验证码并获取
        String code = GetMessageCode.getCode(mobile + "");
        Regist regist2 = new Regist();
        regist2.setPhone(mobile);
        regist2.setCode(Integer.parseInt(code));
        regist2.setData(new Date(System.currentTimeMillis()));
        //如果是第一次获取验证码
        if(regist1.isEmpty()){
            int i = registMapper.insert(regist2);
            if(i>0){
                return R.ok("验证码成功");
            }else {
                return R.error("验证码获取失败");
            }
         //如果不是第一次获取验证码
        }else {
            regist2.setRegistId(regist1.get(0).getRegistId());
            int i = registMapper.updateByPrimaryKeySelective(regist2);

            if(i>0){
                return R.ok("验证码成功");
            }else {
                return R.error("验证码获取失败");
            }

        }

    }

    @Override
    public R register(User user,int code) {

        RegistExample registExample = new RegistExample();

        RegistExample.Criteria criteria = registExample.createCriteria();

        criteria.andPhoneEqualTo(user.getUsMobile());

        List<Regist> regists = registMapper.selectByExample(registExample);

        //验证用户输入的验证码是否正确
        if(!regists.isEmpty()&&regists.get(0).getCode().equals(code)){

            //第一次注册用户名随机生成
            user.setUsName(System.currentTimeMillis()+"");

            user.setUsCreatedate(new Date(System.currentTimeMillis()));

            String password = user.getUsPassword();
            //Md5加密
            Md5Hash md5Hash = new Md5Hash(password,user.getUsMobile()+"",1024);
            user.setUsPassword(md5Hash.toString());
            int insert = userMapper.insert(user);

            if(insert>0){
                return R.ok("注册成功");
            }else {
                return R.error("注册失败");
            }
        }else {
            return R.error("注册失败!!");
        }





    }
    


}
