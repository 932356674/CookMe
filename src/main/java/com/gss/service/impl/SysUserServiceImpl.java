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

    @Override
    public R verifyCode(Regist regist) {

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        criteria.andUsMobileEqualTo(regist.getPhone());

        List<User> users = userMapper.selectByExample(example);

        if (!users.isEmpty()){

            RegistExample registExample = new RegistExample();

            RegistExample.Criteria criteria1 = registExample.createCriteria();

            criteria1.andPhoneEqualTo(regist.getPhone());

            List<Regist> regist1 = registMapper.selectByExample(registExample);

            if(regist.getCode().equals(regist1.get(0).getCode())){
                return R.ok("认证成功");
            }else{
                return R.error("验证码错误");
            }
        }else {
            return R.error("用户不存在");
        }
    }

    @Override
    public R resetPwd(User user) {

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsMobileEqualTo(user.getUsMobile());
        List<User> users = userMapper.selectByExample(example);
        Md5Hash md5Hash = new Md5Hash(user.getUsPassword(),user.getUsMobile()+"",1024);
        users.get(0).setUsPassword(md5Hash.toString());
        int i = userMapper.updateByPrimaryKeySelective(users.get(0));
        if(i>0){
            return R.ok("重置成功");
        }
        return R.error("重置失败");
    }


    @Override
    public User login(long phone) {
        UserExample example=new UserExample();
        UserExample.Criteria criteria=example.createCriteria();

        criteria.andUsMobileEqualTo(phone);
        List<User> list=userMapper.selectByExample(example);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<User> findMobile(long phone) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        criteria.andUsMobileEqualTo(phone);

        List<User> users = userMapper.selectByExample(example);

        return users;
    }

    @Override
    public R mobileLogin(Regist regist) {

        RegistExample registExample=new RegistExample();
        RegistExample.Criteria criteria=registExample.createCriteria();
        criteria.andPhoneEqualTo(regist.getPhone());
        List<Regist> list=registMapper.selectByExample(registExample);

        if(list.size()>0&&list.get(0).getCode().equals(regist.getCode())){
            return R.ok();
        }
        return R.error("登录失败");
    }
}
